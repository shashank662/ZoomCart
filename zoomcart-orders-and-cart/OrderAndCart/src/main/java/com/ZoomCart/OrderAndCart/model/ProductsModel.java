package com.ZoomCart.OrderAndCart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsModel {
    private String id;
    private String productName;
    private int merchantId;
    private int quantity;
}
