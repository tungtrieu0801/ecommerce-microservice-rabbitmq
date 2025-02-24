package com.example.warehouseservice.service.Impl;

import com.example.warehouseservice.FileUtil;
import com.example.warehouseservice.base.BaseService;
import com.example.warehouseservice.entity.Brand;
import com.example.warehouseservice.entity.Image;
import com.example.warehouseservice.payload.request.BrandRequest;
import com.example.warehouseservice.repository.BrandRepository;
import com.example.warehouseservice.repository.ImageRepository;
import com.example.warehouseservice.service.BrandService;

import com.example.warehouseservice.utils.Base64Util;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.UUID;
@Service
public class BrandServiceImpl extends BaseService<Brand, UUID> implements BrandService {

    private final BrandRepository brandRepository;

    private final ModelMapper modelMapper;

    private final ImageRepository imageRepository;

    public BrandServiceImpl(BrandRepository brandRepository, ModelMapper modelMapper, ImageRepository imageRepository) {
        this.brandRepository = brandRepository;
        this.modelMapper = modelMapper;
        this.imageRepository = imageRepository;
        setRepository(brandRepository);
    }

    @Transactional
    @Override
    public Brand addBrandWithAvatar(BrandRequest brandRequest, MultipartFile file) {
        try {
            String uploadDir = FileUtil.getUploadPath();
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String middleDir = DateFormatUtils.format(new Date(), "/yyyy/MM/");
            String fileExt = FilenameUtils.getExtension(fileName);

            String encodedFileName = Base64Util.encodeString(fileName);
            String originalFilePath = uploadDir + middleDir + encodedFileName + "." + fileExt;
            String thumbnailFilePath = uploadDir + middleDir + encodedFileName + "_thumbnail." + fileExt;

            File originalFile = new File(originalFilePath);
            File thumbnailFile = new File(thumbnailFilePath);

            if (!originalFile.getParentFile().exists()) {
                originalFile.getParentFile().mkdirs();
            }

            file.transferTo(originalFile);
            long originalSize = originalFile.length();

            Thumbnails.of(originalFile)
                    .size(300, 300)
                    .outputQuality(1.0)
                    .toFile(thumbnailFile);
            long thumbnailSize = thumbnailFile.length();

            Image originalImage = imageRepository.save(
                    Image.builder()
                            .name(fileName)
                            .type(fileExt)
                            .size(String.valueOf(originalSize))
                            .saveName(middleDir + encodedFileName + "." + fileExt)
                            .build()
            );

            Image thumbnailImage = imageRepository.save(
                    Image.builder()
                            .name("Thumbnail of " + fileName)
                            .type(fileExt)
                            .size(String.valueOf(thumbnailSize))
                            .saveName(middleDir + encodedFileName + "_thumbnail." + fileExt)
                            .build()
            );

            Brand brand = modelMapper.map(brandRequest, Brand.class);
            brand.setAvatar(String.valueOf(originalImage.getId()));
            brand.setThumbnail(String.valueOf(thumbnailImage.getId()));

            return brandRepository.save(brand);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload brand avatar", e);
        }
    }
}
