package com.tmax.eTest.Contents.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeDTO {
  private String codeId;
  private String domain;
  private String code;
  private String name;
}
