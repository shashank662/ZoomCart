package com.ZoomCart.MerchantProduct.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(doNotUseGetters = true)
public class GGModel {
    private List<AllProductsPerMerchantModel> productsPerMerchantModelList;
}
