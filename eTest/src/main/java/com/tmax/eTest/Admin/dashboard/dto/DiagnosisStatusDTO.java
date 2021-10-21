package com.tmax.eTest.Admin.dashboard.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisStatusDTO {
    private String[] time;
    private int[] diagnosisCollect;
    private int[] diagnosisAndMinitest;
    private int[] diagnosisTotal;
    private double[] diagnosisTotalRatio;
    private int[] diagnosisMember;
    private double[] diagnosisMemberRatio;
    private int[] diagnosisNotMember;
    private double[] diagnosisNotMemberRatio;
    private int[] minitest;
    private double[] minitestRatio;
    private int diagnosisCount;
    private String[] diagnosisScoreLegend;
    private double[] diagnosisScore;
    private int averageGI;
    private int averageRisk;
    private int averageInvest;
    private int averageKnowledge;
    private int minitestCount;
    private String[] minitestScoreLegend;
    private double[] minitestScore;
    private double averageMinitest;
    private List<String> minitestCategory;
    private List<Double> minitestCategoryAverage;
}
