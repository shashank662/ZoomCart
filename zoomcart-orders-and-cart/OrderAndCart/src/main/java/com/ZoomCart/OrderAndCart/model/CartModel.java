package com.ZoomCart.OrderAndCart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartModel {


    private int cartId;
    private int userId;
    private String id;
    private int merchantId;
    private int quantity;
    private String productName;
    private Float price;
    private String productImage;


}
