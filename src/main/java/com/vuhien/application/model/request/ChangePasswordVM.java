package com.vuhien.application.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ChangePasswordVM {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
