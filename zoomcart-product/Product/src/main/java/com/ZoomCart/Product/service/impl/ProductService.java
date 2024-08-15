package com.ZoomCart.Product.service.impl;

import com.ZoomCart.Product.entity.Product;
import com.ZoomCart.Product.model.*;
import com.ZoomCart.Product.repository.ProductRepository;
import com.ZoomCart.Product.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j

@Service
public class ProductService implements IProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    RestTemplate restTemplate;


    public List<ProductModel> findAll() {
        List<Product> products = productRepository.findAllByOrderByPriceAsc();
        List<ProductModel> productModels = new ArrayList<>();

        for (Product product : products) {
            ProductModel productModel = new ProductModel();

            productModel.setId(product.getId());
            productModel.setProductName(product.getProductName());
            productModel.setProductImage(product.getProductImage());
            productModel.setBrand(product.getBrand());
            productModel.setDescription(product.getDescription());
            productModel.setColor(product.getColor());
            productModel.setStorage(product.getStorage());
            productModel.setOs(product.getOs());
            productModel.setPrice(product.getPrice());
            productModel.setQuantity(product.getQuantity());
            productModel.setMerchantId(product.getMerchantId());


            productModels.add(productModel);
        }
        return productModels;
    }

    public MerchantProdModel add(int merchantId, ProductHeaderModel productHeaderModel) {
        Product product = new Product();
        MerchantProdModel merchantProdModel = new MerchantProdModel();
//        product.setProductId(productHeaderModel.getProductId());
        product.setProductName(productHeaderModel.getProductName());
        log.info(productHeaderModel.getProductName());
        product.setProductImage(productHeaderModel.getProductImage());
        product.setBrand(productHeaderModel.getBrand());
        product.setDescription(productHeaderModel.getDescription());
        product.setColor(productHeaderModel.getColor());
        product.setStorage(productHeaderModel.getStorage());
        product.setOs(productHeaderModel.getOs());
        product.setPrice(productHeaderModel.getPrice());
        product.setQuantity(productHeaderModel.getQuantity());
        product.setMerchantId(merchantId);

        Product product1 = productRepository.save(product);

        merchantProdModel.setToken(productHeaderModel.getToken());
        merchantProdModel.setId(product1.getId());
        merchantProdModel.setMerchantId(merchantId);
        merchantProdModel.setPrice(productHeaderModel.getPrice());
//        log.info("Got quantity as: "+ productHeaderModel.getQuantity());
        merchantProdModel.setQuantity(productHeaderModel.getQuantity());


        return restTemplate.postForObject("http://localhost:10400/merchant/put", merchantProdModel, MerchantProdModel.class);
    }

    public void deleteProduct(String productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public void edit(ProductModel productModel) {
        Product product = new Product();
        product.setProductName(productModel.getProductName());
        product.setProductImage(productModel.getProductImage());
        product.setBrand(productModel.getBrand());
        product.setDescription(productModel.getDescription());
        product.setColor(productModel.getColor());
        product.setStorage(productModel.getStorage());
        product.setOs(productModel.getOs());

        productRepository.save(product);
    }

    public void editImg(String productId, ProductModel updatedProductModel) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();

            existingProduct.setProductImage(updatedProductModel.getProductImage());

            productRepository.save(existingProduct);
        } else {
            throw new Exception("Product not found with ID: " + productId);
        }
    }

    @Override
    public void editDescription(String productId, ProductModel updatedProductModel) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();

            existingProduct.setDescription(updatedProductModel.getDescription());

            productRepository.save(existingProduct);
        } else {
            throw new Exception("Product not found with ID: " + productId);
        }
    }

    @Override
    public void editColor(String productId, ProductModel updatedProductModel) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();

            existingProduct.setColor(updatedProductModel.getColor());

            productRepository.save(existingProduct);
        } else {
            throw new Exception("Product not found with ID: " + productId);
        }
    }

    public void editStorage(String productId, ProductModel updatedProductModel) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();

            existingProduct.setStorage(updatedProductModel.getStorage());

            productRepository.save(existingProduct);
        } else {
            throw new Exception("Product not found with ID: " + productId);
        }
    }

    @Override
    public List<ProductModel> findProductByProductName(String productName) {
        List<Product> products = productRepository.findByProductNameContainingIgnoreCase(productName);
        List<ProductModel> productModels = new ArrayList<>();

        for (Product product : products) {
            ProductModel productModel = new ProductModel();

            productModel.setId(product.getId());
            productModel.setProductName(product.getProductName());
            productModel.setProductImage(product.getProductImage());
            productModel.setBrand(product.getBrand());
            productModel.setDescription(product.getDescription());
            productModel.setColor(product.getColor());
            productModel.setStorage(product.getStorage());
            productModel.setOs(product.getOs());
            productModel.setPrice(product.getPrice());
            productModel.setQuantity(product.getQuantity());
            productModel.setMerchantId(product.getMerchantId());


            productModels.add(productModel);
        }
        return productModels;

    }

    @Override
    public ProductModel findById(String productId) {
        Optional<Product> optProduct = productRepository.findById(productId);
        if (optProduct.isPresent()) {
            log.info("I'm here");
            Product product = optProduct.get();
            ProductModel productModel = new ProductModel();
            productModel.setId(product.getId());
            productModel.setProductName(product.getProductName());
            productModel.setProductImage(product.getProductImage());
            productModel.setBrand(product.getBrand());
            productModel.setDescription(product.getDescription());
            productModel.setColor(product.getColor());
            productModel.setStorage(product.getStorage());
            productModel.setOs(product.getOs());

            productModel.setPrice(product.getPrice());
            productModel.setQuantity(product.getQuantity());
            productModel.setMerchantId(product.getMerchantId());

            return productModel;
        }
        return null;
    }

    public List<AllProductsPerMerchantModel> getNames(List<String> productIds) {
        List<AllProductsPerMerchantModel> allProductsPerMerchantModelList = new ArrayList<>();
        for (String id : productIds) {
            AllProductsPerMerchantModel allProductsPerMerchantModel = new AllProductsPerMerchantModel();
            Optional<Product> product = productRepository.findById(id);
            allProductsPerMerchantModel.setProductName(product.get().getProductName());
            allProductsPerMerchantModel.setId(id);
            allProductsPerMerchantModelList.add(allProductsPerMerchantModel);
        }
        return allProductsPerMerchantModelList;
    }

    @Transactional
    public String reduceProduct(List<ProductsModel> productsModel) {
        List<ProductsModel> productsModels1 = new ArrayList<>();
        int i = 0;
        for (ProductsModel productsModel2 : productsModel) {

            Optional<Product> product = productRepository.findById(productsModel2.getId());
            if(product.get().getQuantity() < productsModel2.getQuantity()){
                return "Product Quantity less";
            }
            product.get().setQuantity(product.get().getQuantity() - productsModel2.getQuantity());
            productRepository.save(product.get());
            i++;
//                Product product1 = product.get();
//                String res=restTemplate.postForObject("http://localhost:")
        }
        String res = restTemplate.postForObject("http://localhost:10400/merchant/updateAfterOrder", productsModel, String.class);
        if (!Objects.equals(res, "Done")) {
            return "Couldn't update!";
        }
        log.info("Successfully updated");

        return "Successfully updated";
    }
}
