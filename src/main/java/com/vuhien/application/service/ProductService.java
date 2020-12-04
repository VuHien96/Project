package com.vuhien.application.service;

import com.vuhien.application.entity.Product;
import com.vuhien.application.model.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<ProductDTO> getListProduct();
    ProductDTO getProductById(int productId);
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(ProductDTO productDTO,int productId);
    void deleteProduct(int productId);
    List<ProductDTO> getListProductNew();
    Product getOne(int productId);
    Page<Product> getListProductByCategoryOrProductNameContaining(Pageable pageable,
                                                                  @Param("categoryId") Integer categoryId,
                                                                  @Param("productName") String productName);
}
