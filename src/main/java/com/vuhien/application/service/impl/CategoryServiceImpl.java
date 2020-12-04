package com.vuhien.application.service.impl;

import com.vuhien.application.entity.Category;
import com.vuhien.application.entity.Product;
import com.vuhien.application.exception.DuplicateRecordException;
import com.vuhien.application.exception.InternalServerException;
import com.vuhien.application.exception.NotFoundException;
import com.vuhien.application.model.dto.CategoryDTO;
import com.vuhien.application.model.mapper.CategoryMapper;
import com.vuhien.application.repository.CategoryRepository;
import com.vuhien.application.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getListCategory() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (Category category : categories){
            categoryDTOS.add(CategoryMapper.toCategoryDTO(category));
        }
        return categoryDTOS;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category getOne(int categoryId){
        Category category = categoryRepository.getOne(categoryId);
        return category;
    }

    @Override
    public CategoryDTO getCategoryById(int categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()){
            throw  new NotFoundException("No category found");
        }
        return CategoryMapper.toCategoryDTO(category.get());
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = categoryRepository.findAllByName(categoryDTO.getName());
        if (category != null){
            throw new DuplicateRecordException("Ten danh muc da ton tai");
        }
        category = CategoryMapper.toCategory(categoryDTO);
        categoryRepository.save(category);
        return CategoryMapper.toCategoryDTO(category);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, int categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()){
            throw  new NotFoundException("No category found");
        }
        Category updateCategory = CategoryMapper.toCategory(categoryDTO,categoryId);
        try {
            categoryRepository.save(updateCategory);
        }catch (Exception e){
            throw new InternalServerException("Database error. Can't update category");
        }
        return CategoryMapper.toCategoryDTO(updateCategory);
    }

    @Override
    public void deleteCategory(int categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()) {
            throw new NotFoundException("No category found");
        }
        try {
            categoryRepository.deleteById(categoryId);
        } catch (Exception ex) {
            throw new InternalServerException("Database error. Can't delete category");
        }
    }

    @Override
    public Page<Category> getListCategoryByCategoryNameContaining(Pageable pageable, String categoryName) {
        return categoryRepository.getListCategoryByCategoryNameContaining(pageable,categoryName);
    }
}
