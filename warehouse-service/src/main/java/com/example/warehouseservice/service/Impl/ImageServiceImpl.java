package com.example.warehouseservice.service.Impl;

import com.example.warehouseservice.FileUtil;
import com.example.warehouseservice.entity.Image;
import com.example.warehouseservice.repository.ImageRepository;
import com.example.warehouseservice.service.ImageService;
import jakarta.servlet.ServletContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    private final ServletContext servletContext;

    public ImageServiceImpl(ImageRepository imageRepository, ServletContext servletContext) {
        this.imageRepository = imageRepository;
        this.servletContext = servletContext;
    }

    @Override
    public ResponseEntity<Object> displayImage(String seq) throws UnsupportedEncodingException {
        Optional<Image> file = imageRepository.findById(UUID.fromString(seq));

        String saveName = FileUtil.getUploadPath() + file.get().getSaveName();
        Resource resource = new FileSystemResource(saveName);

        if (resource.exists()) {
            String contentType = null;
            try {
                contentType = servletContext.getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            String fileName = URLEncoder.encode(file.get().getName(), String.valueOf(StandardCharsets.UTF_8)).replace("+", "%20");
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + fileName + ";filename*=UTF-8''" + fileName).body(resource);
        } else {
            return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body("<script>alert('file not found')</script>");
        }
    }

    @Override
    public ResponseEntity<Object> downloadImg(String seq) throws UnsupportedEncodingException {
        Image file = imageRepository.findById(UUID.fromString(seq)).get();


        String saveName = FileUtil.getUploadPath() + file.getSaveName();

        Resource resource = new FileSystemResource(saveName);

        if (resource.exists()) {
            String contentType = null;
            try {
                contentType = servletContext.getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {}

            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            String fileName = URLEncoder.encode(file.getSaveName(), String.valueOf(StandardCharsets.UTF_8)).replace("+", "%20");

            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName + ";filename*= UTF-8''" + fileName).body(resource);
        } else {
            return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body("<script>alert('file not found')</script>");
        }

    }
}
