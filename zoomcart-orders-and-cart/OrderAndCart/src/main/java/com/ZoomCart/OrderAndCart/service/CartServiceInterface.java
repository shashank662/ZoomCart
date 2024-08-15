package com.ZoomCart.OrderAndCart.service;

import com.ZoomCart.OrderAndCart.model.CartModel;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CartServiceInterface {
    String save(CartModel cartModel, int userId);
    List<CartModel> findByUserID(int userId);
@Transactional
    String removeCartItem(String id, int cartId);
}
