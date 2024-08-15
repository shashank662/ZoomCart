package com.ZoomCart.OrderAndCart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel {
    private String id;

    private int userId;

    private Float totalAmount;
    private String timeStamp;

    private List<ProductsModel> productsModel;
}
