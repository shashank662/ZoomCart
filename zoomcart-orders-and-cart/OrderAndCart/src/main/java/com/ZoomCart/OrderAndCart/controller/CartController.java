package com.ZoomCart.OrderAndCart.controller;


import com.ZoomCart.OrderAndCart.entity.Cart;
import com.ZoomCart.OrderAndCart.model.CartModel;
import com.ZoomCart.OrderAndCart.service.CartServiceInterface;
import com.ZoomCart.OrderAndCart.service.impl.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })

@RequestMapping("/cart")

public class CartController {

    @Autowired
    private CartServiceImpl cartService;


    @PostMapping("/add/{userId}")
    public ResponseEntity<String> addToCart(@PathVariable Integer userId,@RequestBody CartModel cartModel) {
        String ans;
        try {
            ans=cartService.save(cartModel, userId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(ans);
    }
    @GetMapping("/{userId}")
    public List<CartModel> getCartItemsForUser(@PathVariable int userId) {
//        System.out.println("Hi");
        return cartService.findByUserID(userId);
    }

    @DeleteMapping("/removeCartItem/{id}/{cartId}")
    public ResponseEntity<String> removeItem(@PathVariable String id, @PathVariable Integer cartId){
        String ans;
        ans=cartService.removeCartItem(id, cartId);
        if (Objects.equals(ans, "Done")) {
            return ResponseEntity.ok(ans);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ans);
        }

    }

    @GetMapping("/change/{userId}/{id}/{pm}")
    public ResponseEntity<?>  updateCount(@PathVariable int userId, @PathVariable String id, @PathVariable String pm){        ////give either plus or minus as pm variable
        String ans=cartService.changeItemInDb(userId, id, pm);
        if("Couldn't add or subtract.".equals(ans)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ans);
        }
        else{
            return ResponseEntity.ok(ans);
        }

    }     //changed this in the other file. Copy it here.

    //add a delete mapping to clear the cart once the order is placed
    @DeleteMapping("/deleteCart/{userId}")
    public ResponseEntity<?> delete(@PathVariable int userId){
        String ans=cartService.deleteCart(userId);
        if(!Objects.equals(ans, "Couldn't add or subtract."))
            return ResponseEntity.ok(ans);
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ans);
        }
    }



}
