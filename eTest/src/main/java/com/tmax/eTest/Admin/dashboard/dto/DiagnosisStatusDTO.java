package com.tmax.eTest.Admin.dashboard.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisStatusDTO {
//    private CollectDTO diagnosisCollect;
    private DiagnosisPerTimeDTO diagnosisPerTime;
    private List<DiagnosisInfoDTO> diagnosisInfo;
    private ReportStatusDTO diagnosisStatus;
    private ReportStatusDTO minitestStatus;
}
