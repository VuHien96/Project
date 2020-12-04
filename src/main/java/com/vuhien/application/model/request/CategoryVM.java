package com.vuhien.application.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CategoryVM {
    private int id;
    private String name;
    private String description;
    private Date createdDate;
    private List<CategoryVM> categoryVMList;
    private String keyWord;
}
