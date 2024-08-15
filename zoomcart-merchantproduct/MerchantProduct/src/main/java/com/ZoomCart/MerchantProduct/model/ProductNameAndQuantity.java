package com.ZoomCart.MerchantProduct.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductNameAndQuantity {
    private String productName;
    private Integer quantity;
}
