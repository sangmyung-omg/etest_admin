package com.tmax.eTest.Admin.dashboard.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectDTO {
    private String[] time;
    private int[] collect;
}
