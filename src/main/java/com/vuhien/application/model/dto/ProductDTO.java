package com.vuhien.application.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vuhien.application.extension.CustomDateDeserializer;
import com.vuhien.application.extension.CustomDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private int productId;
    private int categoryId;
    private String name;
    private String description;
    private String images;
    private Double price;

    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createdDate;
}
