package com.example.warehouseservice.service;

import com.example.warehouseservice.entity.Brand;
import com.example.warehouseservice.payload.request.BrandRequest;
import org.springframework.web.multipart.MultipartFile;

public interface BrandService {

    Brand addBrandWithAvatar(BrandRequest brandRequest, MultipartFile file);
}
