package com.tmax.eTest.KdbStudio.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UkGetOutputDTO {
    private Integer ukId;
    private String ukName;
    private String partName;
    private String ukDescription;
    private String externalLink;
    private Timestamp updateDate;
    private Integer page;
    private List<UkRelatedVideoDTO> relatedVideo;
    private List<UkRelatedArticleDTO> relatedArticle;
}
