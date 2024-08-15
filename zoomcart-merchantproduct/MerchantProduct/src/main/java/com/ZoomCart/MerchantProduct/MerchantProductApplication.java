package com.ZoomCart.MerchantProduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MerchantProductApplication {

  @Bean
  public RestTemplate getTemplate() {
    return new RestTemplate();
  }

  public static void main(String[] args) {
    SpringApplication.run(MerchantProductApplication.class, args);
  }
}
