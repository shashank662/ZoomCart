package com.ZoomCart.Product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductHeaderModel {
    private String id;

    private String productName;

    private String productImage;

    private String brand;

    private String os;

    private String color;

    private int storage;

    private String description;

    private int token;

    private Float price;

    private int quantity;

}
