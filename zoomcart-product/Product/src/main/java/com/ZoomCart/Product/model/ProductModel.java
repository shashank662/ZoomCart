package com.ZoomCart.Product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {
    private String id;

    private String productName;

    private String productImage;

    private String brand;

    private String os;

    private String color;

    private int storage;

    private String description;
    private Float price;

    private int merchantId;

    private int quantity;
}
