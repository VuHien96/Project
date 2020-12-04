package com.vuhien.application.service.impl;

import com.vuhien.application.entity.Product;
import com.vuhien.application.exception.DuplicateRecordException;
import com.vuhien.application.exception.InternalServerException;
import com.vuhien.application.exception.NotFoundException;
import com.vuhien.application.model.dto.ProductDTO;
import com.vuhien.application.model.mapper.ProductMapper;
import com.vuhien.application.repository.CategoryRepository;
import com.vuhien.application.repository.ProductRepository;
import com.vuhien.application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<ProductDTO> getListProduct() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : products) {
            productDTOS.add(ProductMapper.toProductDTO(product));
        }
        return productDTOS;
    }

    @Override
    public ProductDTO getProductById(int productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            throw new NotFoundException("No product found");
        }
        return ProductMapper.toProductDTO(product.get());
    }

    public Product getOne(int productId){
       Optional<Product>  product = productRepository.findById(productId);
        return product.get();
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productRepository.findAllByName(productDTO.getName());
        if (product != null) {
            throw new DuplicateRecordException("Ten san pham da ton tai");
        }

        product = ProductMapper.toProduct(productDTO);
        product.setCategory(categoryRepository.getOne(productDTO.getCategoryId()));
        productRepository.save(product);
        return ProductMapper.toProductDTO(product);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, int productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            throw new NotFoundException("No product found");
        }
        Product updateProduct = ProductMapper.toProduct(productDTO, productId);
        updateProduct.setCategory(categoryRepository.getOne(productDTO.getCategoryId()));
        try {
            productRepository.save(updateProduct);
        } catch (Exception e) {
            throw new InternalServerException("Database error. Can't update product");
        }
        return ProductMapper.toProductDTO(updateProduct);
    }

    @Override
    public void deleteProduct(int productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            throw new NotFoundException("No product found");
        }
        try {
            productRepository.deleteById(productId);
        } catch (Exception ex) {
            throw new InternalServerException("Database error. Can't delete product");
        }
    }

    @Override
    public List<ProductDTO> getListProductNew() {
        List<Product> products = productRepository.getListProductNew();
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : products) {
            productDTOS.add(ProductMapper.toProductDTO(product));
        }
        return productDTOS;
    }

    @Override
    public Page<Product> getListProductByCategoryOrProductNameContaining(Pageable pageable, Integer categoryId, String productName) {
        return productRepository.getListProductByCategoryOrProductNameContaining(pageable,categoryId,productName);
    }
}
