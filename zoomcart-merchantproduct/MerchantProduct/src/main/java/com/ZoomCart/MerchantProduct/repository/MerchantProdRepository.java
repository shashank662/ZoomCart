package com.ZoomCart.MerchantProduct.repository;
import com.ZoomCart.MerchantProduct.entity.MerchantProd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchantProdRepository extends JpaRepository<MerchantProd, Integer> {

    List<MerchantProd> findByMerchantId(Integer merchantId);

    MerchantProd findByIdAndMerchantId(String id, int merchantId);
}
