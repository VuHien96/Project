package com.vuhien.application.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class CartProductDTO {
    private int id;
    private int productId;
    private String guid;
    private String userName;
    private int amount;
}
