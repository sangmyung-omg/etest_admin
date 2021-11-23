package com.tmax.eTest.Contents.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeCreateDTO {
  String domain;
  String code;
  String name;
}
