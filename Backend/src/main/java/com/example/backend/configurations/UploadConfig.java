package com.example.backend.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UploadConfig {
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @Value("${spring.servlet.multipart.max-request-size}")
    private String maxRequestSize;

    public String getMaxFileSize() {
        return maxFileSize;
    }

    public String getMaxRequestSize() {
        return maxRequestSize;
    }
}
