package com.tmax.eTest.Dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StatementDashboardDTO {
    private Timestamp statementDate;
    private String actionType;
    private String sourceType;
}
