package com.ZoomCart.Search.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "product")
public class Product {
    @Id
    //@Field(type = FieldType.Text, name = "id")
    private String id;

    @Field(type = FieldType.Text, name = "productName")
    private String productName;

    @Field(type = FieldType.Text, name = "productImage")
    private String productImage;

    @Field(type = FieldType.Text, name = "brand")
    private String brand;

    @Field(type = FieldType.Text, name = "os")
    private String os;

    @Field(type = FieldType.Text, name = "price")
    private Float price;

    @Field(type = FieldType.Text, name = "color")
    private String color;

    @Field(type = FieldType.Text, name = "storage")
    private String storage;

    @Field(type = FieldType.Text, name = "description")
    private String description;

}
