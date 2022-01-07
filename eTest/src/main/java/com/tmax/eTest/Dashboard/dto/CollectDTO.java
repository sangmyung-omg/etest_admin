package com.tmax.eTest.Dashboard.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectDTO {
    private String[] time;
    private String[] secondaryTime;
    private int[] collect;
}
