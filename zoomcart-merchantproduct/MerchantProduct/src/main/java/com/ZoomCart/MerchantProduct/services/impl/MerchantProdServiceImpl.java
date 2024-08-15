package com.ZoomCart.MerchantProduct.services.impl;

import com.ZoomCart.MerchantProduct.entity.MerchantProd;
import com.ZoomCart.MerchantProduct.model.*;
import com.ZoomCart.MerchantProduct.repository.MerchantProdRepository;
import com.ZoomCart.MerchantProduct.services.MerchantProdService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MerchantProdServiceImpl implements MerchantProdService {
  @Autowired MerchantProdRepository merchantProdRepository;

  @Autowired private ObjectMapper objectMapper;

  @Autowired RestTemplate restTemplate;

  public MerchantProdModel create(MerchantProdModel merchantProdModel) {
    MerchantProd merchantProd1 =
        merchantProdRepository.findByIdAndMerchantId(
            merchantProdModel.getId(), merchantProdModel.getMerchantId());
    if (merchantProd1 == null) {
      MerchantProd merchantProd = new MerchantProd();
      merchantProd.setMerchantId(merchantProdModel.getMerchantId());
      merchantProd.setId(merchantProdModel.getId());

      merchantProd.setPrice(merchantProdModel.getPrice());
      merchantProd.setQuantity(merchantProdModel.getQuantity());

      merchantProdRepository.save(merchantProd);
      return merchantProdModel;
    } else {
      return null;
    }
  }

  public List<ProductNameAndQuantity> getAllMerchantProducts(int merchantId) {
    List<MerchantProd> merchantProd = merchantProdRepository.findByMerchantId(merchantId);
    List<String> productIds = new ArrayList<>();
    List<Integer> quantities = new ArrayList<>();
    for (MerchantProd merchantProd1 : merchantProd) {
      productIds.add(merchantProd1.getId());
      quantities.add(merchantProd1.getQuantity());
    }
    int i = 0;
    JsonNode ggNode =
        restTemplate.postForObject(
            "http://localhost:10200/product/getProdName", productIds, JsonNode.class);
    try {
      List<AllProductsPerMerchantModel> allProductsPerMerchantModelList =
          objectMapper.readValue(ggNode.toString(), new TypeReference<>() {});
      List<ProductNameAndQuantity> productNameAndQuantities = new ArrayList<>();
      if (!allProductsPerMerchantModelList.isEmpty()) {
        for (AllProductsPerMerchantModel allProductsPerMerchantModel :
            allProductsPerMerchantModelList) {
          ProductNameAndQuantity productNameAndQuantity = new ProductNameAndQuantity();
          productNameAndQuantity.setProductName(allProductsPerMerchantModel.getProductName());
          productNameAndQuantity.setQuantity(quantities.get(i));
          i++;
          productNameAndQuantities.add(productNameAndQuantity);
        }
        return productNameAndQuantities;
      }

    } catch (JsonProcessingException ignored) {
    }
    return null;
  }

  public int getQuant(String id, int merchantId) {
    MerchantProd merchantProd = merchantProdRepository.findByIdAndMerchantId(id, merchantId);
    return merchantProd.getQuantity();
  }

  @Transactional
  public String reduceQuantInMerch(List<ProductsModel> productsModelList) {
    try {
      for (ProductsModel productsModel : productsModelList) {
        MerchantProd merchantProd =
            merchantProdRepository.findByIdAndMerchantId(
                productsModel.getId(), productsModel.getMerchantId());
        merchantProd.setQuantity(merchantProd.getQuantity() - productsModel.getQuantity());
        if (merchantProd.getQuantity() < 0) {
          return "Not enough products in the inventory! Try reducing some and then order.";
        }
      }
      return "Done";
    } catch (Exception exc) {
      return "Not Done.";
      //    log.info(exc.printStackTrace());
    }
  }
}
