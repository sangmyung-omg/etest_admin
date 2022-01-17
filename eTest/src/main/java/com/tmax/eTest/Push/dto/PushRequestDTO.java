package com.tmax.eTest.Push.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PushRequestDTO {
    private List<String> token;
    private List<String> userUuid;
    private String category;
    private String title;
    private String body;
    private String image;
    private String url;
}
