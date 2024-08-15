package com.ZoomCart.Product.service;

import com.ZoomCart.Product.model.MerchantProdModel;
import com.ZoomCart.Product.model.ProductHeaderModel;
import com.ZoomCart.Product.model.ProductModel;

import java.util.List;

public interface IProductService {
    List<ProductModel> findAll();
    void deleteProduct(String productId);
    MerchantProdModel add(int merchantId, ProductHeaderModel productHeaderModel);
    void edit(ProductModel productModel);
    void editImg(String productId, ProductModel updatedProductModel) throws Exception;
    void editDescription(String productId, ProductModel updatedProductModel) throws Exception;
    void editColor(String productId, ProductModel updatedProductModel) throws Exception;
    void editStorage(String productId, ProductModel updatedProductModel) throws Exception;
    List<ProductModel> findProductByProductName(String productName);
    ProductModel findById(String productId);
}
