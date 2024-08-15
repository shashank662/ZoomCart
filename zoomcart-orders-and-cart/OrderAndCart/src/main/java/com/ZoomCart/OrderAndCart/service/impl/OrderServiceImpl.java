package com.ZoomCart.OrderAndCart.service.impl;

import com.ZoomCart.OrderAndCart.Component.EmailSender;
import com.ZoomCart.OrderAndCart.entity.Cart;
import com.ZoomCart.OrderAndCart.entity.Order;
import com.ZoomCart.OrderAndCart.model.*;
import com.ZoomCart.OrderAndCart.repository.CartRepository;
import com.ZoomCart.OrderAndCart.repository.OrderRepository;
import jakarta.mail.SendFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class OrderServiceImpl {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartServiceImpl cartService;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    EmailSender emailSender;


    public String placeOrder(Integer userId, Float totalAmount, String timeStamp) throws SendFailedException {
            List<Cart> cartList = cartRepository.findByUserId(userId);
            Order order = new Order();
            order.setUserId(userId);
            order.setTotalAmount(totalAmount);
            order.setTimeStamp(timeStamp);
            List<ProductsModel> productsModelsList = new ArrayList<>();
            for (Cart cart : cartList) {
                ProductsModel productsModel = new ProductsModel();
                productsModel.setId(cart.getId());
                productsModel.setProductName(cart.getProductName());
                productsModel.setMerchantId(cart.getMerchantId());
                productsModel.setQuantity(cart.getQuantity());
                productsModelsList.add(productsModel);
            }
            String updatedOrNot = restTemplate.postForObject("http://localhost:10200/product/updateProduct", productsModelsList, String.class);
            log.info(updatedOrNot);
            if (Objects.equals(updatedOrNot, "Successfully updated")) {
            order.setProductsModel(productsModelsList);
            orderRepository.save(order);
            String ans = cartService.deleteCart(userId);
            String email=restTemplate.postForObject("http://localhost:10100/user/getEmail", userId, String.class);
            List<String> emailBody=makeEmail(productsModelsList, totalAmount);
            String mail=emailSender.sendSimpleMessage(email, "Order Details of id: "+ order.getId(), emailBody);
            if (Objects.equals(ans, "Done")) {
                return "Added list to order table!";
            } else if ("No product to remove!".equals(ans)) {
                return "Didn't delete cart!";
            } else {
                return "Couldn't add list to table.";
            }
        }else if("Product Quantity less".equals(updatedOrNot)){
                return "Product Quantity less";
            }
            else{
            return "Couldn't add list to table, order couldn't be completed. Exception caught at place order.";
        }

    }

    public List<OrderModel> findByUserID(int userId) {
        List<OrderModel> orderModels = new ArrayList<>();
        List<Order> orderList = orderRepository.findByUserId(userId);

        for (Order order : orderList) {
            OrderModel orderModel = new OrderModel();
            orderModel.setId(order.getId());
            orderModel.setUserId(order.getUserId());
            orderModel.setTotalAmount(order.getTotalAmount());
            orderModel.setTimeStamp(order.getTimeStamp());
            orderModel.setProductsModel(order.getProductsModel());
//            orderModel.setQuantity(cart.getQuantity());
//            cartModel.setId(cart.getId());
//            cartModel.setMerchantId(cart.getMerchantId());
            orderModels.add(orderModel);
        }
        return orderModels;
    }

    List<String> makeEmail(List<ProductsModel> productsModelList, Float totalAmount){
        List<String> emailBody=new ArrayList<>();
        for(ProductsModel productsModel: productsModelList){
            String newLine="Product Name: " + productsModel.getProductName() + "\nQuantity: "+productsModel.getQuantity() + "\n";
            emailBody.add(newLine);
        }
        emailBody.add("Total Order Amount: "+ totalAmount);
        return emailBody;
    }
}
