package com.tmax.eTest.Admin.dashboard.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Getter
@Setter
@Builder
public class FilterQueryDTO {
    private Timestamp dateFrom;
    private Timestamp dateTo;
    private String gender;
    private LocalDate ageGroupLowerBound;
    private LocalDate ageGroupUpperBound;
    private int investmentExperience;
}
