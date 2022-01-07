package com.tmax.eTest.Dashboard.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MinitestDashboardDTO {
    private Timestamp minitestDate;
    private Float avgUkMastery;
    private String minitestUkMastery;
}
