package com.vuhien.application.model.mapper;

import com.vuhien.application.entity.Category;
import com.vuhien.application.entity.Product;
import com.vuhien.application.model.dto.ProductDTO;

import java.util.Date;

public class ProductMapper {

    public static ProductDTO toProductDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(product.getProductId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setImages(product.getImages());
        productDTO.setCreatedDate(new Date());
        productDTO.setCategoryId(product.getCategory().getCategoryId());
        return productDTO;
    }

    public static Product toProduct(ProductDTO productDTO){
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImages(productDTO.getImages());
        product.setCreateDate(new Date());
        return product;
    }

    public static Product toProduct(ProductDTO productDTO,int productId){
        Product product = new Product();
        product.setProductId(productId);
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImages(productDTO.getImages());
        product.setCreateDate(new Date());
        return product;
    }
}
