package com.vuhien.application.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductVM {
    private int id;
    private String categoryName;
    private String name;
    private String description;
    private String images;
    private Double price;
    private Date createdDate;
    private List<ProductVM> productVMList;
    private List<CategoryVM> categoryVMList;
    private List<ImageVM> imageVMS;
    private String keyWord;
}
