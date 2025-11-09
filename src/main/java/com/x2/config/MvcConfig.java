package com.x2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
// The problematic import, which is correct in Spring Web MVC:
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Correctly maps the file system path to the web URL path /uploads/**
        String path = "file:///".concat(uploadDir.replace("\\", "/"));

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(path);
    }
}