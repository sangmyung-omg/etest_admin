package com.tmax.eTest.Dashboard.dto;

import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportStatusDTO {
    private int diagnosisCount;
    private String[] scoreLegend;
    private double[] scoreRatio;
    private double averageScore;
    private List<String> categoryName;
    private List<Double> categoryAverageScore;
}
