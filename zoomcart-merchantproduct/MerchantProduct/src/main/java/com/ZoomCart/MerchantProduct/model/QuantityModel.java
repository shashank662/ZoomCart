package com.ZoomCart.MerchantProduct.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor@NoArgsConstructor
public class QuantityModel {
    private String id;
    private int merchantId;
    private int quantity;
}