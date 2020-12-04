package com.vuhien.application.model.mapper;

import com.vuhien.application.entity.Category;
import com.vuhien.application.model.dto.CategoryDTO;

import java.util.Date;

public class CategoryMapper {
    public static CategoryDTO toCategoryDTO(Category category){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(category.getCategoryId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        categoryDTO.setCreatedDate(new Date());

        return categoryDTO;
    }

    public static Category toCategory(CategoryDTO categoryDTO){
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category.setCreatedDate(new Date());

        return category;
    }

    public static Category toCategory(CategoryDTO categoryDTO,int categoryId){
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category.setCreatedDate(new Date());
        return category;
    }
}
