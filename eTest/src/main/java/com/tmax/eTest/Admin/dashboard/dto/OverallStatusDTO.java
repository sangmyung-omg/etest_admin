package com.tmax.eTest.Admin.dashboard.dto;

import lombok.*;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OverallStatusDTO {
    private int accessorCollect;
//    private int memberCountOfChange;
    private int memberRegistered;
//    private int memberWithdrawn;
    private int memberTotal;
    private int diagnosisTotal;
    private int diagnosis;
    private int minitest;
    private String[] giScoreLegend;
    private double[] giScoreRatio;
    private String[] minitestScoreLegend;
    private double[] minitestScoreRatio;
    private ServiceUsageDTO serviceUsageRatio;
}
