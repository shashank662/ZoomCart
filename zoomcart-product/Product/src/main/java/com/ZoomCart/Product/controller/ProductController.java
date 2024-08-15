package com.ZoomCart.Product.controller;

import com.ZoomCart.Product.model.AllProductsPerMerchantModel;
import com.ZoomCart.Product.model.ProductHeaderModel;
import com.ZoomCart.Product.model.ProductModel;
import com.ZoomCart.Product.model.ProductsModel;
import com.ZoomCart.Product.service.impl.ProductService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired private RedisTemplate<String, String> redisTemplate;
    @GetMapping(value = "/getAllProducts")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping(value = "/getProductByName")
    public ResponseEntity<List<ProductModel>> getProductByName(@RequestParam String productName) {
        System.out.println(productName);
        List<ProductModel> products = productService.findProductByProductName(productName);
        System.out.println(products);
        return ResponseEntity.ok(products);
    }

    @PostMapping(value = "/merchantAddNewProduct")
    public ResponseEntity<String> addProduct(@RequestParam int merchantId,
                //@RequestHeader("Authorization") String token,
                                             @RequestBody ProductHeaderModel productHeaderModel) {
//        if (!validateToken(token)) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
//        }
        if (productService.add(merchantId, productHeaderModel) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Product added");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Product already exists. Edit it!");
        }
    }

    @GetMapping(value = "/{productId}")
    public ProductModel getProductById(@PathVariable String productId) {
        ProductModel product = productService.findById(productId);
        return product;
        //return ResponseEntity.ok(products);
    }

    @PostMapping("/getProdName")
    public List<AllProductsPerMerchantModel> getProducts(@RequestBody List<String> productIds){
        return productService.getNames(productIds);
    }
    @PutMapping(value = "/editProduct")
    public ResponseEntity<String> editProduct(@RequestBody ProductModel productModel) {
        productService.edit(productModel);
        return ResponseEntity.ok("Product updated");
    }

    @PutMapping(value = "/editProduct/{productId}/productDescription")
    public ResponseEntity<String> editProductDescription(@PathVariable("productId") String productId,
            @RequestBody ProductModel updatedProductModel) throws Exception {

        productService.editDescription(productId, updatedProductModel);

        return ResponseEntity.ok("Product description updated");
    }

    @PutMapping(value = "/editProduct/{productId}/color")
    public ResponseEntity<String> editProductColor(@PathVariable("productId") String productId,
                                                 @RequestBody ProductModel updatedProductModel) throws Exception {

        productService.editColor(productId, updatedProductModel);

        return ResponseEntity.ok("Product color updated");
    }

    @PutMapping(value = "/editProduct/{productId}/storage")
    public ResponseEntity<String> editProductStorage(@PathVariable("productId") String productId,
                                                 @RequestBody ProductModel updatedProductModel) throws Exception {

        productService.editStorage(productId, updatedProductModel);

        return ResponseEntity.ok("Product storage updated");
    }

    @PutMapping(value = "/editProduct/{productId}/productImg")
    public ResponseEntity<String> editProductImg(@PathVariable("productId") String productId,
                                                 @RequestBody ProductModel updatedProductModel) throws Exception {

        productService.editImg(productId, updatedProductModel);

        return ResponseEntity.ok("Product image updated");
    }



    @DeleteMapping(value = "/deleteProduct/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    private String generateToken(String email, Key key) {
        long expirationTime = 5 * 60 * 1000;
        String token =
                Jwts.builder()
                        .setSubject(email)
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                        .signWith(SignatureAlgorithm.HS512, key)
                        .compact();
        redisTemplate.opsForValue().set(token, email, expirationTime, TimeUnit.MILLISECONDS);
        return token;
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

    @PostMapping("/updateProduct")
    public String reduceProductQuantity(@RequestBody List<ProductsModel> productsModel){
        return productService.reduceProduct(productsModel);
    }
}
