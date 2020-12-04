package com.vuhien.application.repository;

import com.vuhien.application.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {

//    @Query(value = "SELECT * FROM cart c " +
//            "WHERE :guid IS NULL OR c.guid = :guid " +
//            "ORDER BY c.cart_id DESC LIMIT 1",nativeQuery = true)
//    Cart findFirstCartByGuid(@Param("guid") String guid);


    @Query(value = "SELECT * from cart c " +
            "WHERE :userName IS NULL OR c.username = :userName " +
            "ORDER BY c.cart_id DESC LIMIT 1",nativeQuery = true)
    Cart findByUserName(@Param("userName") String userName);
}
