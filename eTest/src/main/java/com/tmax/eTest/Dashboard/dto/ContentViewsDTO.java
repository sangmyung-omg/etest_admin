package com.tmax.eTest.Dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentViewsDTO {
    private String[] time;
    private String[] secondaryTime;
    private int[] viewsVideo;
    private int[] viewsArticle;
    private int[] viewsTextbook;
//    private int[] viewsWiki;
}
