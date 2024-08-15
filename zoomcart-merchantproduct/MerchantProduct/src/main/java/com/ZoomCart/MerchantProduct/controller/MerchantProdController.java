package com.ZoomCart.MerchantProduct.controller;

import com.ZoomCart.MerchantProduct.entity.MerchantProd;
import com.ZoomCart.MerchantProduct.model.AllProductDetails;
import com.ZoomCart.MerchantProduct.model.MerchantProdModel;
import com.ZoomCart.MerchantProduct.model.ProductNameAndQuantity;
import com.ZoomCart.MerchantProduct.model.ProductsModel;
import com.ZoomCart.MerchantProduct.services.MerchantProdService;
import com.ZoomCart.MerchantProduct.services.impl.MerchantProdServiceImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin(
    origins = "*",
    allowedHeaders = "*",
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping(value = "/merchant")
public class MerchantProdController {
  @Autowired MerchantProdServiceImpl merchantProdServiceImpl;

  @Autowired private RedisTemplate<String, String> redisTemplate;

  @PostMapping("/put")
  public ResponseEntity<MerchantProdModel> handleGetMerchant(
      @RequestBody MerchantProdModel merchantProdModel) {
    MerchantProdModel merchantProd = merchantProdServiceImpl.create(merchantProdModel);
    if (merchantProd != null) {
      return ResponseEntity.ok(merchantProd);
    } else {
      return ResponseEntity.ofNullable(null);
    }
  }

  @GetMapping("/getAllMerchantProducts/{merchantId}")
  public ResponseEntity<?> getMerchProds(@PathVariable Integer merchantId)
      {
    List<ProductNameAndQuantity> productNameAndQuantityList =
        merchantProdServiceImpl.getAllMerchantProducts(merchantId);
    if (productNameAndQuantityList.isEmpty()) {
      return null;
    }
    return ResponseEntity.ok(productNameAndQuantityList);
  }

  private boolean validateToken(String token) {
    String storedEmail = redisTemplate.opsForValue().get(token);

    return storedEmail == null || !storedEmail.equals(getUsernameFromToken(token));
  }

  private String getUsernameFromToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(Keys.secretKeyFor(SignatureAlgorithm.HS512))
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  @GetMapping("/checkQuantity/{id}/{merchantId}")
  public int getQuant(@PathVariable String id, @PathVariable int merchantId) {
    return merchantProdServiceImpl.getQuant(id, merchantId);
  }

  @PostMapping("/updateAfterOrder")
  public String reduceQuant(@RequestBody List<ProductsModel> productsModelList) {
    return merchantProdServiceImpl.reduceQuantInMerch(productsModelList);
  }
}
