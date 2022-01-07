package com.tmax.eTest.Dashboard.dto;

import lombok.*;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceUsageDTO {
    private double diagnosis;
    private double minitest;
    private double video;
    private double article;
    private double textbook;
}
