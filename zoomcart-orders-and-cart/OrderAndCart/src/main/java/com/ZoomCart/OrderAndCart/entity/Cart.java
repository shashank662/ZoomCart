package com.ZoomCart.OrderAndCart.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token")
    private int token;

    @Column(name = "cartId")
    private int cartId;
    @Column(name = "userId")
    private int userId;
    @Column(name = "id")
    private String id;
    @Column(name = "merchantId")
    private int merchantId;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "productName")
    private String productName;
    @Column(name = "price")
    private Float price;
    @Column(name = "productImage")
    private String productImage;

}
