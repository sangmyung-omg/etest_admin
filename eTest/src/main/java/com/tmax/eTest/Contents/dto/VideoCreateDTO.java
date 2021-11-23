package com.tmax.eTest.Contents.dto;

import java.sql.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoCreateDTO {
  private String videoType;
  private String videoSrc;
  private String title;
  private String imgSrc;
  private Float totalTime;
  private Float startTime;
  private Float endTime;
  @DateTimeFormat(iso = ISO.DATE)
  private Date createDate;
  @DateTimeFormat(iso = ISO.DATE)
  private Date registerDate;
  @DateTimeFormat(iso = ISO.DATE)
  private Date endDate;
  private String related;
  private String show;
  private List<Long> ukIds;
  private List<String> hashtags;
  private String classification;
  private String largeArea;
  private String groupArea;
  private String detailArea;
  private String particleArea;
  private String serialNum;
  private String level;
  private String difficulty;
  private String source;
  private Integer views;
  private Integer likes;
  private Integer disLikes;
  @DateTimeFormat(iso = ISO.DATE)
  private Date viewDate;

}
