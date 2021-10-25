package com.tmax.eTest.Admin.dashboard.dto;

import lombok.*;

@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisInfoDTO {
    private String time;
    private int diagnosisAndMinitest;
    private int diagnosisTotal;
    private double diagnosisTotalRatio;
    private int diagnosisMember;
    private double diagnosisMemberRatio;
    private int diagnosisNotMember;
    private double diagnosisNotMemberRatio;
    private int minitest;
    private double minitestRatio;
}
