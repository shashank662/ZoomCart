package com.ZoomCart.OrderAndCart.service.impl;


import com.ZoomCart.OrderAndCart.entity.Cart;
import com.ZoomCart.OrderAndCart.model.CartModel;
import com.ZoomCart.OrderAndCart.repository.CartRepository;
import com.ZoomCart.OrderAndCart.service.CartServiceInterface;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CartServiceImpl implements CartServiceInterface {
    @Autowired
    CartRepository cartRepository;

    public String save(CartModel cartModel, int userId) {

        Cart cart = new Cart();

        cart.setUserId(userId);
        cart.setCartId(userId);
        cart.setMerchantId(cartModel.getMerchantId());
        cart.setQuantity(1);
        cart.setId(cartModel.getId());
        cart.setProductName(cartModel.getProductName());
        cart.setProductImage(cartModel.getProductImage());
        cart.setPrice(cartModel.getPrice());
        cartRepository.save(cart);
        return "Successful";
    }


    public List<CartModel> findByUserID(int userId) {

        List<CartModel> cartModels = new ArrayList<>();
        List<Cart> cartList = cartRepository.findByUserId(userId);
        for (Cart cart : cartList) {
            CartModel cartModel = new CartModel();
            cartModel.setProductImage(cart.getProductImage());
            cartModel.setPrice(cart.getPrice());
            cartModel.setProductName(cart.getProductName());
            cartModel.setUserId(cart.getUserId());
            cartModel.setCartId(cartModel.getUserId());
            cartModel.setQuantity(cart.getQuantity());
            log.info(String.valueOf(cartModel.getQuantity()));
            cartModel.setId(cart.getId());
            cartModel.setMerchantId(cart.getMerchantId());
            cartModels.add(cartModel);
        }
        return cartModels;
    }

    @Transactional
    public String removeCartItem(String id, int cartId) {
        try {
            cartRepository.deleteByIdAndCartId(id, cartId);
            return "Done";
        } catch (Exception exc) {
            return null;
        }
    }

    @Transactional
    public String changeItemInDb(int userId, String id, String pm) {        ////give either plus or minus as pm variable
        //must update in both cart table
            Cart cart = cartRepository.findByUserIdAndId(userId, id);
            if("plus".equals(pm)) {
                cart.setQuantity(cart.getQuantity() + 1);
                return "Added 1 to quantity";
            }
            else if("minus".equals(pm)){
                cart.setQuantity(cart.getQuantity()-1);
                return "Subtracted 1 from quantity";
            }
        return "Couldn't add or subtract";
    }

    @Transactional
    public String deleteCart(int userId){
        try{
            Integer i =cartRepository.deleteByUserId(userId);
            if(i>=1) {
                return "Done";
            }else{
                return "No product to remove!";
            }
        }catch(Exception exc){
            return "Couldn't delete the cart";
        }
    }
}
