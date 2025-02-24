package com.example.warehouseservice.controller;

import com.example.warehouseservice.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/displayImg/{seq}")
    public ResponseEntity<Object> displayImage(@PathVariable(value = "seq") String seq) throws UnsupportedEncodingException {
        return imageService.displayImage(seq);
    }

    @GetMapping("/downloadImg/{seq}")
    public ResponseEntity<Object> downloadImg(@PathVariable(value = "seq") String seq) throws UnsupportedEncodingException {
        return imageService.downloadImg(seq);
    }

}
