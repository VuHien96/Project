package com.vuhien.application.controller.client;

import com.vuhien.application.model.request.ProductVM;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class DefaultController {

    @GetMapping("/login")
    public String login(@ModelAttribute("productname") ProductVM productName) {
        return "client/login";
    }

    @GetMapping("/order")
    public String order(  @ModelAttribute("productname") ProductVM productName){
        return "client/checkout";
    }

    @GetMapping("/register")
    public String register(){
        return "client/registers";

    }

    @GetMapping("/user-detail")
    public String userdetail(){
        return "client/user-detail";

    }

    @GetMapping("/403")
    public String error(){
        return "client/403";
    }
}
