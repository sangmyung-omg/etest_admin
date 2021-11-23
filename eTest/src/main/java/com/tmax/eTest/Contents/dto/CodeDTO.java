package com.tmax.eTest.Contents.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CodeDTO extends CodeCreateDTO {
  String codeId;

  @Builder
  public CodeDTO(String domain, String code, String name, String codeId) {
    super(domain, code, name);
    this.codeId = codeId;
    this.code = code;
  }
}
