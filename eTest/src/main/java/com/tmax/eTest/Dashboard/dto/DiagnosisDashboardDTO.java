package com.tmax.eTest.Dashboard.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisDashboardDTO {
    private Timestamp diagnosisDate;
    private String userUuid;
    private Integer giScore;
    private Integer riskScore;
    private Integer investScore;
    private Integer knowledgeScore;
}
