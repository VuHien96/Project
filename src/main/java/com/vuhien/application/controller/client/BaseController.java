package com.vuhien.application.controller.client;

import com.vuhien.application.entity.Cart;
import com.vuhien.application.service.impl.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.UUID;

public class BaseController {

    @Autowired
    private CartService cartService;

    public void checkCookie(HttpServletResponse response,
                            HttpServletRequest request, Principal principal) {

        Cookie cookie[] = request.getCookies();

        if (principal != null) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Cart cartEntity = cartService.findByUserName(username);
            if (cartEntity != null) {
                Cookie cookie1 = new Cookie("user", cartEntity.getUserName());
                cookie1.setPath("/");
                response.addCookie(cookie1);
            } else {
                Cart cart = new Cart();
                cart.setUserName(username);
                cartService.createCart(cart);

                Cookie cookie2 = new Cookie("user", username);
                cookie2.setPath("/");
                response.addCookie(cookie2);
            }
        }
    }
}
