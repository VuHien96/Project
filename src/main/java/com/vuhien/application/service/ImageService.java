package com.vuhien.application.service;

import com.vuhien.application.model.dto.ImageDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImageService {
    List<ImageDTO> getListImages();
    ImageDTO getImagetById(int imageId);
    ImageDTO createImage(ImageDTO imageDTO);
    ImageDTO updateImage(ImageDTO imageDTO ,int imageId);
    void deleteImage(int imageId);
}
