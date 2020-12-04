package com.vuhien.application.model.mapper;

import com.vuhien.application.entity.Images;
import com.vuhien.application.model.dto.ImageDTO;

import java.util.Date;

public class ImageMapper {
    public static ImageDTO toImageDTO(Images images) {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setId(images.getImageId());
        imageDTO.setLink(images.getLink());
        imageDTO.setProductId(images.getProduct().getProductId());

        return imageDTO;
    }

    public static Images toImages(ImageDTO imageDTO) {
        Images images = new Images();
        images.setLink(imageDTO.getLink());
        images.setCreatedDate(new Date());

        return images;
    }

    public static Images toImages(ImageDTO imageDTO, int imageId) {
        Images images = new Images();
        images.setImageId(imageId);
        images.setLink(imageDTO.getLink());
        images.setCreatedDate(new Date());

        return images;
    }

}
