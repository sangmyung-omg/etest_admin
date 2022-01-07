package com.tmax.eTest.Dashboard.dto;

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
public class FilterRepoQueryDTO {
    private String timeUnit;
    private Timestamp dateFrom;
    private Timestamp dateTo;
//    private String gender;
//    private LocalDate ageGroupLowerBound;
//    private LocalDate ageGroupUpperBound;
//    private int investmentExperience;
}
