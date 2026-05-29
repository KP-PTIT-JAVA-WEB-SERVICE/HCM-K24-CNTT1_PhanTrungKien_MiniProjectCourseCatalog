package com.ptit.course_catalog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir:./uploads}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        File directory = new File(uploadDir);
        String absPath = directory.getAbsolutePath();
        if (!absPath.endsWith(File.separator) && !absPath.endsWith("/")) {
            absPath += "/";
        }
        String resourceLocation = "file:" + absPath;

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(resourceLocation);
    }
}
