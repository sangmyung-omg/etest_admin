package com.tmax.eTest.Admin.faq.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateFaqDto {
    private String category;

    private String title;

    private String content;

    private MultipartFile image;
}
