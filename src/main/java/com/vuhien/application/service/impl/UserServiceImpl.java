package com.vuhien.application.service.impl;

import com.vuhien.application.entity.User;
import com.vuhien.application.exception.DuplicateRecordException;
import com.vuhien.application.model.dto.UserDTO;
import com.vuhien.application.model.mapper.UserMapper;
import com.vuhien.application.repository.UserRepository;
import com.vuhien.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.StreamSupport;

@Component
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
   private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getListUser() {
        return null;
    }

    @Override
    public User getUserById(int id) {
        User user = userRepository.getOne(id);
        return user;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userRepository.findByEmail(userDTO.getEmail());
        if(user != null) {
            throw new DuplicateRecordException("Email is already in use!");
        }
        String passwordEncode = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(passwordEncode);
        user = UserMapper.toUser(userDTO);
        userRepository.save(user);
        return UserMapper.toUserDto(user);
    }

    @Override
    public Boolean updateUser( User user) {
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public UserDTO removeUser(int id) {
        return null;
    }

    @Override
    public User findByUseremail(String email) {
        return StreamSupport
                .stream(userRepository.findByUseremail(email).spliterator(),false)
                .findFirst().orElse(null);
    }


}
