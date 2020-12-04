package com.vuhien.application.service.impl;

import com.vuhien.application.entity.Images;
import com.vuhien.application.entity.Product;
import com.vuhien.application.exception.InternalServerException;
import com.vuhien.application.exception.NotFoundException;
import com.vuhien.application.model.dto.ImageDTO;
import com.vuhien.application.model.mapper.ImageMapper;
import com.vuhien.application.model.mapper.ProductMapper;
import com.vuhien.application.repository.ImageRepository;
import com.vuhien.application.repository.ProductRepository;
import com.vuhien.application.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ImageDTO> getListImages() {
        List<Images> imagesList = imageRepository.findAll();
        List<ImageDTO> imageDTOS = new ArrayList<>();
        for (Images images: imagesList) {
            imageDTOS.add(ImageMapper.toImageDTO(images));
        }
        return imageDTOS;
    }

    @Override
    public ImageDTO getImagetById(int imageId) {
        Optional<Images> images = imageRepository.findById(imageId);
        if (!images.isPresent()){
            throw new NotFoundException("No images found");
        }
        return ImageMapper.toImageDTO(images.get());
    }

    @Override
    public ImageDTO createImage(ImageDTO imageDTO) {
        Images images = ImageMapper.toImages(imageDTO);
        images.setProduct(productRepository.getOne(imageDTO.getProductId()));
        imageRepository.save(images);
        return ImageMapper.toImageDTO(images);
    }

    @Override
    public ImageDTO updateImage(ImageDTO imageDTO, int imageId) {
        Optional<Images> images = imageRepository.findById(imageId);
        if (!images.isPresent()) {
            throw new NotFoundException("No images found");
        }
        Images updateImages = ImageMapper.toImages(imageDTO, imageId);
        updateImages.setProduct(productRepository.getOne(imageDTO.getProductId()));
        try {
            imageRepository.save(updateImages);
        } catch (Exception e) {
            throw new InternalServerException("Database error. Can't update images");
        }
        return ImageMapper.toImageDTO(updateImages);
    }

    @Override
    public void deleteImage(int imageId) {
        Optional<Images> images = imageRepository.findById(imageId);
        if (!images.isPresent()) {
            throw new NotFoundException("No images found");
        }
        try {
            imageRepository.deleteById(imageId);
        } catch (Exception ex) {
            throw new InternalServerException("Database error. Can't delete images");
        }
    }
}
