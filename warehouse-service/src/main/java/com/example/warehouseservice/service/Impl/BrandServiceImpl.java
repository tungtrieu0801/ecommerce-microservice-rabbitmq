package com.example.warehouseservice.service.Impl;

import com.example.warehouseservice.base.BaseService;
import com.example.warehouseservice.entity.Brand;
import com.example.warehouseservice.entity.Image;
import com.example.warehouseservice.payload.request.BrandRequest;
import com.example.warehouseservice.repository.BrandRepository;
import com.example.warehouseservice.repository.ImageRepository;
import com.example.warehouseservice.service.BrandService;

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
            String uploadDir = getUploadPath();
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String middleDir = DateFormatUtils.format(new Date(), "/yyyy/MM/");
            String uniqueFileName = UUID.randomUUID() + "." + FilenameUtils.getExtension(fileName);
            String destPath = uploadDir + middleDir;
            File destDir = new File(destPath);

            if (!destDir.exists() && !destDir.mkdirs()) {
                throw new IOException("Failed to create directories: " + destPath);
            }

            File destFile = new File(destDir, uniqueFileName);
//            file.transferTo(destFile);
            appendFile(file.getInputStream(), destFile);
            long size = destFile.length();

            Image fileDo = imageRepository.save(
                    Image.builder()
                            .name(fileName)
                            .type(FilenameUtils.getExtension(fileName))
                            .size(String.valueOf(size))
                            .build()
            );

            Brand brand = modelMapper.map(brandRequest, Brand.class);
            return brandRepository.save(brand);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload brand avatar", e);
        }
    }

    // Dùng cho ghi nối tiếp.
    private void appendFile(InputStream inputStream, File destFile) {

        boolean append = destFile.exists();

        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(destFile, append))) {
            inputStream = new BufferedInputStream(inputStream);

            int len;
            byte[] buffer = new byte[8192];
            while ((len = inputStream.read(buffer)) >0) {
                outputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getUploadPath() {
        String webRoot = System.getProperty("webRoot");

        if (StringUtils.isEmpty(webRoot)) {
            webRoot = "C:\\Users\\nguye\\project";
        }
        File file = new File(webRoot);
        String uploadDir = file.getParent() != null ? file.getParent() + File.separator + "upload" : webRoot + File.separator + "upload";
        return uploadDir;
    }


}
