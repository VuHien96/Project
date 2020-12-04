package com.vuhien.application.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserVM {
    private int id;

    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    private String avatar;

    private String password;
}
