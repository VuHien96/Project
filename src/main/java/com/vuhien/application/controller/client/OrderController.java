package com.vuhien.application.controller.client;

import com.vuhien.application.entity.*;
import com.vuhien.application.model.request.*;
import com.vuhien.application.security.CustomUserDetails;
import com.vuhien.application.security.JwtUserDetailsService;
import com.vuhien.application.service.impl.CartProductService;
import com.vuhien.application.service.impl.CartService;
import com.vuhien.application.service.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/order")
public class OrderController extends BaseController {

    @Autowired
    private JwtUserDetailsService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartProductService cartProductService;

    @GetMapping("/checkout")
    public String checkout(Model model,
                           @Valid @ModelAttribute("productname") ProductVM productName,
                           final Principal principal) {

        OrderVM order = new OrderVM();
        if(principal!= null) {
            String  username = SecurityContextHolder.getContext().getAuthentication().getName();
            CustomUserDetails userEntity = (CustomUserDetails) userService.loadUserByUsername(username);
            if(userEntity!= null) {
                order.setAddress(userEntity.getUser().getAddress());
                order.setCustomerName(userEntity.getUser().getName());
                order.setPhoneNumber(userEntity.getUser().getPhoneNumber());
                order.setEmail(userEntity.getUser().getEmail());
            }
        }

        model.addAttribute("order",order);
        return "client/checkout";
    }

    @PostMapping("/checkout")
    public String checkout(@Valid @ModelAttribute("order") OrderVM orderVM,
                           @ModelAttribute("productname") ProductVM productName,
                           final Principal principal) {

        Orders order = new Orders();
        String user = null;

            double totalPrice = 0;

            if(principal != null) {
                String  username = SecurityContextHolder.getContext().getAuthentication().getName();
                order.setUserName(username);
            }

            order.setAddress(orderVM.getAddress());
            order.setEmail(orderVM.getEmail());
            order.setPhoneNumber(orderVM.getPhoneNumber());
            order.setCustomerName(orderVM.getCustomerName());
            order.setCreatedDate(new Date());


            Cart cartEntity = cartService.findByUserName(user);
            if(cartEntity != null) {
                List<OrderProduct> orderProducts = new ArrayList<>();
                for (CartProduct cartProduct : cartEntity.getListCartProducts()) {
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setOrder(order);
                    orderProduct.setProduct(cartProduct.getProduct());
                    orderProduct.setAmount(cartProduct.getAmount());

                    double price = cartProduct.getAmount() * cartProduct.getProduct().getPrice();
                    totalPrice += price;

                    orderProduct.setPrice(price);

                    orderProducts.add(orderProduct);
                }

                order.setListProductOrders(orderProducts);
                order.setPrice(totalPrice);

                orderService.addNewOrder(order);

                cartService.deleteCart(cartEntity.getId());
            }

        return "redirect:/order/history";
    }

    @GetMapping("/history")
    public String orderHistory(Model model,
                               @Valid @ModelAttribute("productname") ProductVM productName,
                               HttpServletResponse response,
                               HttpServletRequest request,
                               final Principal principal) {

        OrderHistoryVM vm = new OrderHistoryVM();

        DecimalFormat df = new DecimalFormat("###,###.###");

        List<OrderVM> orderVMS = new ArrayList<>();

        List<Orders> orderEntityList = null;

        if(principal != null) {
            String  username = SecurityContextHolder.getContext().getAuthentication().getName();
            orderEntityList = orderService.findOrderByGuidOrUserName(null,username);
        }
        if(orderEntityList != null) {
            for(Orders order : orderEntityList) {
                OrderVM orderVM = new OrderVM();
                orderVM.setId(order.getId());
                orderVM.setCustomerName(order.getCustomerName());
                orderVM.setEmail(order.getEmail());
                orderVM.setAddress(order.getAddress());
                orderVM.setPhoneNumber(order.getPhoneNumber());
                orderVM.setPrice(df.format(order.getPrice()));
                orderVM.setCreatedDate(order.getCreatedDate());

                orderVMS.add(orderVM);
            }
        }

        vm.setOrderVMS(orderVMS);
        model.addAttribute("vm",vm);

        return "client/order-history";
    }

    @GetMapping("/history/{orderId}")
    public String getOrderDetail(Model model,
                                 @Valid @ModelAttribute("productname") ProductVM productName,
                                 @PathVariable("orderId") int orderId) {

        OrderDetailVM vm = new OrderDetailVM();

        DecimalFormat df = new DecimalFormat("###,###.###");

        double totalPrice = 0;

        List<OrderProductVM> orderProductVMS = new ArrayList<>();

        Orders orderEntity = orderService.findOne(orderId);

        if(orderEntity != null) {
            for(OrderProduct orderProduct : orderEntity.getListProductOrders()) {
                OrderProductVM orderProductVM = new OrderProductVM();

                orderProductVM.setProductId(orderProduct.getProduct().getProductId());
                orderProductVM.setMainImage(orderProduct.getProduct().getImages());
                orderProductVM.setAmount(orderProduct.getAmount());
                orderProductVM.setName(orderProduct.getProduct().getName());

                orderProductVM.setPrice(df.format(orderProduct.getPrice()));

                totalPrice += orderProduct.getPrice();

                orderProductVMS.add(orderProductVM);
            }
        }

        vm.setOrderProductVMS(orderProductVMS);
        vm.setTotalPrice(df.format(totalPrice));
        vm.setTotalProduct(orderProductVMS.size());

        model.addAttribute("vm",vm);

        return "client/order-detail";
    }



}
