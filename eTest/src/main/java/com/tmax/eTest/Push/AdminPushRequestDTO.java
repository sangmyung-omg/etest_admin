package com.tmax.eTest.Push;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AdminPushRequestDTO {
    private List<String> token;
    private String category;
    private String title;
    private String body;
    private String image;
}
