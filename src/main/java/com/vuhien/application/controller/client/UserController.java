package com.vuhien.application.controller.client;

import com.vuhien.application.entity.User;
import com.vuhien.application.model.request.ChangePasswordVM;
import com.vuhien.application.model.request.ProductVM;
import com.vuhien.application.model.request.UserVM;
import com.vuhien.application.security.CustomUserDetails;
import com.vuhien.application.security.JwtUserDetailsService;
import com.vuhien.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/detail")
    public String getUserDetail(@Valid @ModelAttribute("productname") ProductVM productName,
                                Model model,
                                HttpServletResponse response,
                                HttpServletRequest request,
                                final Principal principal) {


        this.checkCookie(response,request,principal);

        UserVM userVM = new UserVM();

        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        CustomUserDetails user = (CustomUserDetails) jwtUserDetailsService.loadUserByUsername(username);

        userVM.setAddress(user.getUser().getAddress());
        userVM.setAvatar(user.getUser().getAvatar());
        userVM.setEmail(user.getUser().getEmail());
        userVM.setName(user.getUser().getName());
        userVM.setPhoneNumber(user.getUser().getPhoneNumber());

        model.addAttribute("user",userVM);

        return "client/user-detail";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            User userEntity = userService.findByUseremail(username);

            userEntity.setAddress(user.getAddress());
            userEntity.setAvatar(user.getAvatar());
            userEntity.setEmail(user.getEmail());
            userEntity.setName(user.getName());
            userEntity.setPhoneNumber(user.getPhoneNumber());

            userService.updateUser(userEntity);

            return "redirect:/user/detail?updateSuccess";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/user/detail?updateFail";
    }

    @GetMapping("/change-password")
    public String changePassWord(@Valid @ModelAttribute("productname") ProductVM productName,
                                 Model model) {

        ChangePasswordVM changePasswordVM = new ChangePasswordVM();
        model.addAttribute("changePassword", changePasswordVM);
        return "client/change-password";
    }

    @PostMapping("change-password")
    public String changePassWordPost(@Valid @ModelAttribute("changePassword") ChangePasswordVM password) {

        try {
            if(password.getCurrentPassword() != null && password.getNewPassword() != null && password.getConfirmPassword() != null) {
                String  username = SecurityContextHolder.getContext().getAuthentication().getName();

                User userEntity = userService.findByUseremail(username);

                if(passwordEncoder.matches(password.getCurrentPassword(),userEntity.getPassword()) == true) {
                    if(password.getNewPassword().equals(password.getConfirmPassword())) {
                        userEntity.setPassword(passwordEncoder.encode(password.getNewPassword()));
                        userService.updateUser(userEntity);
                        return "redirect:/user/change-password?success";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/user/change-password?fail";

    }
}
