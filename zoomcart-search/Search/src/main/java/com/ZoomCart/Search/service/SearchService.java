package com.ZoomCart.Search.service;

import com.ZoomCart.Search.entity.Product;
import com.ZoomCart.Search.model.ProductModel;
import com.ZoomCart.Search.repository.SearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class SearchService {

    @Autowired
    SearchRepository searchRepository;

    public List<Product> searchProducts(List<ProductModel> products,String query) {
        searchRepository.deleteAll();

        for(ProductModel productModel:products){
            Product product = new Product();

            product.setId(productModel.getId());
            product.setProductName(productModel.getProductName());
            product.setProductImage(productModel.getProductImage());
            product.setBrand(productModel.getBrand());
            product.setPrice(productModel.getPrice());
            product.setDescription(productModel.getDescription());
            product.setColor(productModel.getColor());
            product.setStorage(productModel.getStorage());
            product.setOs(productModel.getOs());

            searchRepository.save(product);
        }
        return searchRepository.findByProductNameOrBrandOrOsOrColorOrStorageFuzzyAndOrderByPriceAsc(query);
    }

}
