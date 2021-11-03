package com.tmax.eTest.Admin.dashboard.dto;

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
    private int[] viewsVideo;
    private int[] viewsArticle;
    private int[] viewsTextbook;
//    private int[] viewsWiki;
}
