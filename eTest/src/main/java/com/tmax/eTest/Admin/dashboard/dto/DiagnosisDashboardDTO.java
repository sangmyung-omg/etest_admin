package com.tmax.eTest.Admin.dashboard.dto;

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
