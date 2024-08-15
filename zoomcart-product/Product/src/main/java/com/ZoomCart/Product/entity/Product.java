package com.ZoomCart.Product.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Product")
public class Product {
    @Id
    private String id;

    @Field(name = "productName")
    private String productName;

    @Field(name = "productImage")
    private String productImage;

    @Field(name = "brand")
    private String brand;

    @Field(name = "os")
    private String os;
    @Field(name = "color")
    private String color;

    @Field(name = "storage")
    private int storage;

    @Field(name = "description")
    private String description;

    @Field(name = "price")
    private Float price;

    @Field(name = "merchantId")
    private int merchantId;

    @Field(name = "quantity")
    private int quantity;

}
