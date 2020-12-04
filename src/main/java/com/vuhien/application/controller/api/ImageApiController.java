package com.vuhien.application.controller.api;

import com.vuhien.application.model.api.BaseApiResult;
import com.vuhien.application.model.dto.ImageDTO;
import com.vuhien.application.model.dto.ProductDTO;
import com.vuhien.application.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ImageApiController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/images")
    public ResponseEntity<?> getListImages() {
        List<ImageDTO> imageDTOS = imageService.getListImages();
        return ResponseEntity.ok(imageDTOS);
    }

    @PostMapping("/images")
    public ResponseEntity<?> createImage(@Valid @RequestBody ImageDTO imageDTO) {
        BaseApiResult result = new BaseApiResult();
        try {
            imageService.createImage(imageDTO);
            result.setData(imageDTO.getLink());
            result.setMessage("Thêm mới thành công: " + imageDTO.getLink());
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/images/{id}")
    public ResponseEntity<?> updateImage(@RequestBody ImageDTO imageDTO, @PathVariable int id) {
        BaseApiResult result = new BaseApiResult();
        try {
            imageService.updateImage(imageDTO, id);
            result.setSuccess(true);
            result.setMessage("Cập nhật thành công");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/images/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable int id){
        BaseApiResult result = new BaseApiResult();
        try {
            imageService.deleteImage(id);
            result.setMessage("Xóa thành công !");
            result.setSuccess(true);
        }catch (Exception e){
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<?> getListImageById(@PathVariable int id){
        BaseApiResult result= new BaseApiResult();
        try {
            ImageDTO imageDTO = imageService.getImagetById(id);
            result.setSuccess(true);
            result.setData(imageDTO);
            result.setMessage("Success");
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return ResponseEntity.ok(result);
    }
}
