package com.tmax.eTest.Dashboard.dto;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
public class FilterDTO {
    private String timeUnit; // hour, day, week, month
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
}


