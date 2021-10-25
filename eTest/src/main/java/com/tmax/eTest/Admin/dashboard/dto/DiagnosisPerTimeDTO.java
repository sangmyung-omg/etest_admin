package com.tmax.eTest.Admin.dashboard.dto;

import lombok.*;

@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisPerTimeDTO {
    private String[] time;
    private int[] diagnosisMember;
    private int[] diagnosisNotMember;
    private int[] minitest;
}
