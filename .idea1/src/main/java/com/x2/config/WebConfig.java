package com.x2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // serve uploaded files from filesystem uploads/ directory
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
        // serve css/js from static folder
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
    }
}
