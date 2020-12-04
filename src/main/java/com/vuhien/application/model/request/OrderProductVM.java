package com.vuhien.application.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderProductVM {
    private int productId;
    private String mainImage;
    private int amount;
    private String price;
    private String name;
}
