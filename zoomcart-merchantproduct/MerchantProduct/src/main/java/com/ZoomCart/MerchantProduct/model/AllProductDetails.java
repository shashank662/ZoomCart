package com.ZoomCart.MerchantProduct.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor@NoArgsConstructor
public class AllProductDetails {
    private Float totalAmount;
    private String timeStamp;
    private List<ProductsModel> productsModel;
}