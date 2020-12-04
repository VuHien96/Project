package com.vuhien.application.controller.api;

import com.vuhien.application.model.request.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/link/")
public class LinkController {

    @Autowired
    FileStorageService storageService;

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.loadFile(filename);
        MediaType type = MediaType.ALL;

        if(filename.toLowerCase().endsWith("png")) {
            type = MediaType.IMAGE_PNG;
        } else if (filename.toLowerCase().endsWith("jpg") || filename.toLowerCase().endsWith("jpeg")) {
            type = MediaType.IMAGE_JPEG;
        } else {
            type = MediaType.IMAGE_GIF;
        }

        return ResponseEntity.ok()
                .contentType(type)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

}
