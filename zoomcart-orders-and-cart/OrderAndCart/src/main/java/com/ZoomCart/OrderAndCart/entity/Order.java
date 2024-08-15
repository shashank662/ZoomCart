package com.ZoomCart.OrderAndCart.entity;

import com.ZoomCart.OrderAndCart.model.ProductsModel;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Order")
public class Order {
    @Id
    private String id;
    @Field(value = "userId")
    private int userId;
    @Field(value = "totalAmount")
    private Float totalAmount;
    @Field(value = "timeStamp")
    private String timeStamp;
    @Field(value = "products")
    private List<ProductsModel> productsModel;
}
