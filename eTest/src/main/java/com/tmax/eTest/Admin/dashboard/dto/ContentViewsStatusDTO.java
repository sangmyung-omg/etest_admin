package com.tmax.eTest.Admin.dashboard.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentViewsStatusDTO {
    private ContentViewsDTO contentViews;
    private List<ContentViewsInfoDTO> contentViewsInfo;
}
