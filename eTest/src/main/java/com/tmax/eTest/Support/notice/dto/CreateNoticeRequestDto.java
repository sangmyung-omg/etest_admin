package com.tmax.eTest.Support.notice.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateNoticeRequestDto {
    private String title;

    private String content;

    private MultipartFile image;
}
