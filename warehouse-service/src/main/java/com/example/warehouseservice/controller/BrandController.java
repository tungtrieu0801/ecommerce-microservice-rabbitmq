package com.example.warehouseservice.controller;

import com.example.warehouseservice.base.BaseController;
import com.example.warehouseservice.base.BaseService;
import com.example.warehouseservice.entity.Brand;
import com.example.warehouseservice.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
