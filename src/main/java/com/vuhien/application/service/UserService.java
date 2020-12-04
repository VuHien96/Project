package com.vuhien.application.service;

import com.vuhien.application.entity.User;
import com.vuhien.application.model.dto.UserDTO;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
     List<UserDTO> getListUser();

     User getUserById(int id);

    UserDTO createUser(UserDTO userDTO);

    Boolean updateUser( User user);

    UserDTO removeUser(int id);

    User findByUseremail(@Param("email") String email);
}
