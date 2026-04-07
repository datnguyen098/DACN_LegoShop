package com.fpoly.springbootdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;
// class cấu hình cho spring MVC
// đọc các file tĩnh, file ảnh hoặc các file như js, css, html
// map ping url, đường dẫn
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
// lấy giá trị file application.properties
    // sẽ lấy file từ mục uploads
    @Value("${upload.path}")
    private String uploadPath;
// nơi khai báo thư mục nào sẽ trở tới thư mục nào
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String absolutePath = Paths.get(uploadPath).toAbsolutePath().toString();
// /uploads/** URL trên trình duyệt
        registry.addResourceHandler("/uploads/**")
                // lấy đường dẫn thật trên máy
                .addResourceLocations("file:" + absolutePath + "/")
                // nocache
                .setCacheControl(CacheControl.noCache());
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/", "classpath:/public/");
    }
}