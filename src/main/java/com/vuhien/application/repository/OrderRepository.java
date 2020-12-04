package com.vuhien.application.repository;


import com.vuhien.application.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Integer> {
    @Query("SELECT o FROM Orders o " +
            "WHERE (:guid IS NULL OR (o.guid = :guid))" +
            "AND (:userName IS NULL OR (o.userName = :userName))")
    List<Orders> findOrderByGuidOrUserName(@Param("guid") String guid, @Param("userName") String userName);

    @Query(value = "select * from Orders ",nativeQuery = true)
   Page<Orders> getListOrder(Pageable pageable);
}
