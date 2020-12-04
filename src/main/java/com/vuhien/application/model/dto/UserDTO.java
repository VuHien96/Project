package com.vuhien.application.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int id;

    private String name;

    private String email;

    private String phone;

    private String address;

    private String avatar;

    private String password;

}
