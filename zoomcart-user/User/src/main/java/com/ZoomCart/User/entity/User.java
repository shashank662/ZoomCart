package com.ZoomCart.User.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name= "userId")
    private int userId;

    @Column(name= "password")
    private String password;
    @Column(name= "email")
    private String email;
    @Column(name= "role")
    private String role;

}
