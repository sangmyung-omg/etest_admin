package com.tmax.eTest.Admin.dashboard.dto;

import lombok.*;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentViewsStatusDTO {
    private String[] time;
    private int[] viewsTotal;
    private int[] viewsVideo;
    private double[] viewsVideoRatio;
    private int[] viewsArticle;
    private double[] viewsArticleRatio;
    private int[] viewsTextbook;
    private double[] viewsTextbookRatio;
    private int[] viewsWiki;
    private double[] viewsWikiRatio;
}
