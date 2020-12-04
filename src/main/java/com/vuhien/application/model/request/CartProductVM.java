package com.vuhien.application.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartProductVM {

    private int id;
    private int productId;
    private String mainImage;
    private int amount;
    private String productName;
    private String price;

}
