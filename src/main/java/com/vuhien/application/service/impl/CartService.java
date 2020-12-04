package com.vuhien.application.service.impl;

import com.vuhien.application.entity.Cart;
import com.vuhien.application.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public void createCart(Cart cart) {
        cartRepository.save(cart);
    }

    public Cart findOne(int cartId) {
        return cartRepository.getOne(cartId);
    }


    public boolean updateCart(Cart cart) {
        try {
            cartRepository.save(cart);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCart(int cartId) {
        try {
            cartRepository.deleteById(cartId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public Cart findByUserName(String userName) {
        try {
            return cartRepository.findByUserName(userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
