package com.tmax.eTest.Push.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryPushRequestDTO {
    private String category;
    private String title;
    private String body;
    private String image;
}
