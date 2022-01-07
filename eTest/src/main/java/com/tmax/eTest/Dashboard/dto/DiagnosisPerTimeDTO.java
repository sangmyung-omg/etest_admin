package com.tmax.eTest.Dashboard.dto;

import lombok.*;

@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisPerTimeDTO {
    private String[] time;
    private String[] secondaryTime;
    private int[] diagnosisMember;
    private int[] diagnosisNotMember;
    private int[] minitest;
}
