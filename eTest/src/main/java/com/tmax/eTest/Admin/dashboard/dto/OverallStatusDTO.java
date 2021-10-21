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
    private int memberCountOfChange;
    private int memberRegistered;
    private int memberWithdrawn;
    private int memberTotal;
    private int diagnosisTotal;
    private int diagnosis;
    private int minitest;
//    private String[] diagnosisCategoryName;
//    private double[] diagnosisCategoryRatio;
//    private String[] minitestCategoryName;
//    private double[] minitestCategoryRatio;
    private ServiceUsageDTO serviceUsage;
}
