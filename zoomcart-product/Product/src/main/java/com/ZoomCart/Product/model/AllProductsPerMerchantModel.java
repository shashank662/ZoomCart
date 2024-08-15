package com.ZoomCart.Product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllProductsPerMerchantModel {
    private String id;
    private String productName;
}