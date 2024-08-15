package com.ZoomCart.MerchantProduct.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MerchProd")
public class MerchantProd {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="token")
    private int token;
    @Column(name= "merchantId")
    private int merchantId;
    @Column(name= "id")
    private String id;
    @Column(name= "price")
    private Float price;
    @Column(name= "quantity")
    private int quantity;

}

