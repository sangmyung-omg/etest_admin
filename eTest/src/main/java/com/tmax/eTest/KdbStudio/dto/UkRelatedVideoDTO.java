package com.tmax.eTest.KdbStudio.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UkRelatedVideoDTO {
    private String videoId;
    private String videoSrc;
    private String title;
    private String imgSrc;
    private Float totalTime;
    private Integer views;    
}
