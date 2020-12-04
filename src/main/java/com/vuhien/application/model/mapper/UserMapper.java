package com.vuhien.application.model.mapper;

import com.vuhien.application.entity.User;
import com.vuhien.application.model.dto.UserDTO;

import java.util.Date;

public class UserMapper {
    public static UserDTO toUserDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getUserId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setAddress(user.getAddress());
        userDTO.setPhone(user.getPhoneNumber());
        userDTO.setAvatar(user.getAvatar());
        return userDTO;
    }

    public static User toUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
        user.setPhoneNumber(userDTO.getPhone());
        user.setPassword(userDTO.getPassword());
        user.setRole("USER");
        user.setCreatedDate(new Date());
        return user;
    }
}
