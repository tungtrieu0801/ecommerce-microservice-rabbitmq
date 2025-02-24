package com.example.warehouseservice;

import org.springframework.util.StringUtils;

import java.io.File;

public class FileUtil {
    public FileUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String getUploadPath() {
        String webRoot = System.getProperty("webRoot");

        if (StringUtils.isEmpty(webRoot)) {
            webRoot = "C:\\Users\\nguye\\project";
        }
        File file = new File(webRoot);
        String uploadDir = file.getParent() != null ? file.getParent() + File.separator + "upload" : webRoot + File.separator + "upload";
        return uploadDir;
    }

}