package com.tmax.eTest.Dashboard.dto;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
public class FilterDTO {
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private String gender;
    private Integer ageGroup;
    private int investmentExperience;
}


