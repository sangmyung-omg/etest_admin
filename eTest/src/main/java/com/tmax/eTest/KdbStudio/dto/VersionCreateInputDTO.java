package com.tmax.eTest.KdbStudio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VersionCreateInputDTO {
    private String versionName;
    private String versionNum;
}
