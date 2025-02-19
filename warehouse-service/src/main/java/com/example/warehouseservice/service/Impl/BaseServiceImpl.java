package com.example.warehouseservice.service.Impl;

import com.example.warehouseservice.base.BaseService;
import com.example.warehouseservice.entity.Brand;
import com.example.warehouseservice.repository.BrandRepository;
import com.example.warehouseservice.service.BrandService;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class BaseServiceImpl extends BaseService<Brand, UUID> implements BrandService {

    private final BrandRepository brandRepository;

    public BaseServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
        setRepository(brandRepository);
    }


}
