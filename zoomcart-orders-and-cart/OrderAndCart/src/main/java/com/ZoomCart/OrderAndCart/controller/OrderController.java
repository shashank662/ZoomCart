package com.ZoomCart.OrderAndCart.controller;

import com.ZoomCart.OrderAndCart.entity.Order;
import com.ZoomCart.OrderAndCart.model.AllProductDetails;
import com.ZoomCart.OrderAndCart.model.OrderModel;
import com.ZoomCart.OrderAndCart.service.impl.OrderServiceImpl;
import jakarta.mail.SendFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderServiceImpl orderService;
    @PostMapping("/placeOrder/{userId}/{totalAmount}/{timeStamp}")
    public ResponseEntity<String> placeOrder(@PathVariable Integer userId, @PathVariable Float totalAmount, @PathVariable String timeStamp) throws SendFailedException {
        String ans=orderService.placeOrder(userId, totalAmount, timeStamp);
        if("Added list to order table!".equals(ans)){
            return ResponseEntity.ok(ans);
        }else if("Product Quantity less".equals(ans)){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/orderHistory/{userId}")
    public List<OrderModel> getOrderItemsForUser(@PathVariable int userId){
        return orderService.findByUserID(userId);
    }
}
