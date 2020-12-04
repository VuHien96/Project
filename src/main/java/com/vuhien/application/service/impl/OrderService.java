package com.vuhien.application.service.impl;

import com.vuhien.application.entity.Orders;
import com.vuhien.application.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;


    public void addNewOrder(Orders order) {
        orderRepository.save(order);
    }

    public Orders findOne(int orderId) {
        return orderRepository.getOne(orderId);
    }

    public List<Orders> findOrderByGuidOrUserName(String guid, String userName) {
        return orderRepository.findOrderByGuidOrUserName(guid,userName);
    }

    public Page<Orders> getListOrder(Pageable pageable){
        return orderRepository.getListOrder(pageable);
    }
}
