package com.ZoomCart.Product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MerchantProdModel {
    private int token;
    private int merchantId;
    private String id;
    private Float price;
    private int quantity;

}
