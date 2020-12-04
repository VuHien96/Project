package com.vuhien.application.controller.api;

import com.vuhien.application.model.api.BaseApiResult;
import com.vuhien.application.model.request.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api")
public class UploadApiController {

    @Autowired
    FileStorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        String message = "";
        BaseApiResult result = new BaseApiResult();
        try {
            String newFilename = storageService.store(file);
            message = "You successfully uploaded " +
                    file.getOriginalFilename() + "!";
            result.setMessage(message);
            result.setSuccess(true);
            result.setLink("/link/" +
                    newFilename);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

}
