package com.tmax.eTest.KdbStudio.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UkRelatedArticleDTO {
    private String articleId;
    private String articleSrc;
    private String title;
    private String imgSrc;
    private Float totalTime;
    private Integer views;
}
