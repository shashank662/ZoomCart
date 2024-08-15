package com.ZoomCart.Search.controller;

import com.ZoomCart.Search.entity.Product;
import com.ZoomCart.Search.model.ProductModel;
import com.ZoomCart.Search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/search")
@RestController
public class SearchController {
    @Autowired
    SearchService searchService;

    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/products")
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam String query
    ) {

        String words[] = query.split(" ");
        ResponseEntity<List<ProductModel>> responseEntity = restTemplate.exchange(
                "http://localhost:10200/product/getProductByName?productName={productName}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                words[0]
        );

        List<ProductModel> products = responseEntity.getBody();

        List<Product> productList = searchService.searchProducts(products, query);


        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

}
