package com.tmax.eTest.KdbStudio.dto;

import lombok.Data;

@Data
public class VersionGetOutputDTO {
    private Long versionId;
    private String versionName;
    private String versionNum;
    private Integer isDefault;
    private Integer isDeleted;
}
