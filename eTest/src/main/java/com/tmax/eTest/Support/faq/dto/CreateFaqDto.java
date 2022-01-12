package com.tmax.eTest.Support.faq.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateFaqDto {
    private int draft;

    private String category;

    private String title;

    private String content;

    private MultipartFile image;
}
