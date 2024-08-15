package com.ZoomCart.MerchantProduct.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantProdModel {
    private int merchantId;
    private String id;
    private Float price;
    private int quantity;

}
