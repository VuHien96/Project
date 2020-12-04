package com.vuhien.application.service.impl;

import com.vuhien.application.entity.CartProduct;
import com.vuhien.application.repository.CartProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartProductService {

    @Autowired
    private CartProductRepository cartProductRepository;

    public void createCartProduct(CartProduct cartProduct){
        cartProductRepository.save(cartProduct);
    }

    public boolean updateCartProduct(CartProduct cartProduct){
        try {
            cartProductRepository.save(cartProduct);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public CartProduct findOne(int cartProductId) {
        return cartProductRepository.getOne(cartProductId);
    }

    public boolean deleteCartProduct(int cartProductId) {
        try {
            cartProductRepository.deleteById(cartProductId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public CartProduct findFirstCartProductByCartIdAndProductId(int cartId, int productId) {
        try {
            return cartProductRepository.findFirstCartProductByCartIdAndProductId(cartId,productId);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
