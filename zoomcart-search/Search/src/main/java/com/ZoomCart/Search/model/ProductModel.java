package com.ZoomCart.Search.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {
    private String id;

    private String productName;

    private String productImage;

    private String brand;

    private String os;

    private Float price;
    private String color;

    private String storage;

    private String description;
}
