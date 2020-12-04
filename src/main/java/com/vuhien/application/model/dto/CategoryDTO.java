package com.vuhien.application.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vuhien.application.entity.Product;
import com.vuhien.application.extension.CustomDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private int categoryId;
    private String name;
    private String description;
    private List<Product> productList = new ArrayList<>();

    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createdDate;
}
