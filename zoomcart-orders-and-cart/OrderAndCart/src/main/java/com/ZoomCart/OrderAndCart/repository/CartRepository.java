package com.ZoomCart.OrderAndCart.repository;

import com.ZoomCart.OrderAndCart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    //    @Query("SELECT c FROM CartMariadb c WHERE c.userId = :userId")
    List<Cart> findByUserId(int userId);
    void deleteByIdAndCartId(String id, int cartId);
    Cart findByUserIdAndId(Integer userId, String id);

    Integer deleteByUserId(Integer userId);
}
