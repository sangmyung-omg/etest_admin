package com.tmax.eTest.Admin.dashboard.dto;

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
}
