package com.tmax.eTest.Contents.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CodeSet {
  private Integer views;
  private Integer likes;
  private Integer disLikes;
  private String viewDate;
  private String serialNum;
  private List<String> codes;
}
