package com.example.warehouseservice.controller;

import com.example.warehouseservice.base.BaseController;
import com.example.warehouseservice.base.BaseService;
import com.example.warehouseservice.entity.Brand;
import com.example.warehouseservice.payload.request.BrandRequest;
import com.example.warehouseservice.service.BrandService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/brand")
public class BrandController extends BaseController<Brand, UUID> {

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
        super.setService((BaseService<Brand, UUID>) brandService);
    }

    @PostMapping("/brand/a")
    public ResponseEntity<?> addBrand(
            @RequestParam("file") MultipartFile file,
            @RequestParam("brand") String brandRequest) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        BrandRequest brandRequest1 = objectMapper.readValue(brandRequest, BrandRequest.class);

        return ResponseEntity.ok(brandService.addBrandWithAvatar(brandRequest1, file));
    }

}
