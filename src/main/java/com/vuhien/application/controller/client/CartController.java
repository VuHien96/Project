package com.vuhien.application.controller.client;

import com.vuhien.application.entity.Cart;
import com.vuhien.application.entity.CartProduct;
import com.vuhien.application.model.request.CartProductVM;
import com.vuhien.application.model.request.CartVM;
import com.vuhien.application.model.request.ProductVM;
import com.vuhien.application.service.impl.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {

    @Autowired
    private CartService cartService;

    @GetMapping("/add")
    public String cart(Model model,
                       @Valid @ModelAttribute("productname") ProductVM productName,
                       HttpServletResponse response,
                       HttpServletRequest request, Principal principal) {
        this.checkCookie(response,request,principal);

        CartVM vm = new CartVM();

        int productAmount = 0;
        double totalPrice = 0;

        List<CartProductVM> cartProductVMS = new ArrayList<>();
        String username = getUser(request);
        DecimalFormat df = new DecimalFormat("###,###.###");

        try {
            if (principal != null) {
                Cart cartEntity = cartService.findByUserName(username);
                if (cartEntity != null) {
                    productAmount = cartEntity.getListCartProducts().size();
                    for (CartProduct cartProduct : cartEntity.getListCartProducts()) {
                        CartProductVM cartProductVM = new CartProductVM();
                        cartProductVM.setId(cartProduct.getId());
                        cartProductVM.setProductId(cartProduct.getProduct().getProductId());
                        cartProductVM.setProductName(cartProduct.getProduct().getName());
                        cartProductVM.setMainImage(cartProduct.getProduct().getImages());
                        cartProductVM.setAmount(cartProduct.getAmount());
                        double price = cartProduct.getAmount() * cartProduct.getProduct().getPrice();
                        cartProductVM.setPrice(df.format(price));
                        totalPrice += price;
                        cartProductVMS.add(cartProductVM);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        vm.setProductAmount(productAmount);
        vm.setTotalPrice(df.format(totalPrice));
        vm.setCartProductVMS(cartProductVMS);

        model.addAttribute("vm", vm);

        return "client/cart";


    }

    public String getUser(HttpServletRequest request) {
        Cookie cookie[] = request.getCookies();

        if (cookie != null) {
            for (Cookie c : cookie) {
                if (c.getName().equals("username")) return c.getValue();
            }
        }
        return null;
    }
}
