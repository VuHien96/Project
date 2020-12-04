package com.vuhien.application.controller.api;

import com.vuhien.application.model.api.BaseApiResult;
import com.vuhien.application.model.dto.UserDTO;
import com.vuhien.application.security.JwtTokenUtil;
import com.vuhien.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserApiController {

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO) {
        BaseApiResult result = new BaseApiResult();
        try {
            userService.createUser(userDTO);
            result.setSuccess(true);
            result.setMessage("Đăng ký thành công!");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> login(@Valid @RequestBody UserDTO userDTO, HttpServletRequest request,
                                   HttpServletResponse response) {
        BaseApiResult result = new BaseApiResult();
        try {
            // Xác thực từ username và password.
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDTO.getEmail(),
                            userDTO.getPassword()
                    )
            );

            // Nếu không xảy ra exception tức là thông tin hợp lệ
            // Set thông tin authentication vào Security Context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Gen token
            String token = jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());

            result.setSuccess(true);
            result.setMessage("Đăng nhập thành công !");

            Cookie jwtToken = new Cookie("jwt_token", token);
            jwtToken.setMaxAge(60*60*24);
            jwtToken.setPath("/");
            response.addCookie(jwtToken);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("Email or password không chính xác!");
        }


        return ResponseEntity.ok(result);
    }


}
