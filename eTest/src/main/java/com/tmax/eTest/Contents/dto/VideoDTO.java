package com.tmax.eTest.Contents.dto;

import java.sql.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
// @Builder
// @AllArgsConstructor
public class VideoDTO extends VideoCreateDTO {
  private String videoId;
  private List<String> uks;
  private String subject;
  private Integer hit;
  private Boolean bookmark;
  // private String videoType;
  // private String source;
  private String description;

  @Builder
  public VideoDTO(String videoSrc, String videoType, String title, String imgSrc, Float totalTime, Float startTime,
      Float endTime, Date createDate, Date registerDate, Date endDate, String related, String show, List<Long> ukIds,
      List<String> hashtags, String classification, String largeArea, String groupArea, String detailArea,
      String particleArea, String serialNum, String level, String difficulty, String source, Integer views,
      Integer likes, Integer disLikes, Date viewDate, String videoId, List<String> uks, String subject, Integer hit,
      Boolean bookmark, String description) {
    super(videoSrc, videoType, title, imgSrc, totalTime, startTime, endTime, createDate, registerDate, endDate, related,
        show, ukIds, hashtags, classification, largeArea, groupArea, detailArea, particleArea, serialNum, level,
        difficulty, source, views, likes, disLikes, viewDate);
    this.videoId = videoId;
    this.uks = uks;
    this.subject = subject;
    this.hit = hit;
    this.bookmark = bookmark;
    // this.videoType = videoType;
    // this.source = source;
    this.description = description;
  }

  // private String videoSrc;
  // private String title;
  // private String imgSrc;
  // @DateTimeFormat(iso = ISO.DATE)
  // private Date createDate;
  // @DateTimeFormat(iso = ISO.DATE)
  // private Date registerDate;
  // @DateTimeFormat(iso = ISO.DATE)
  // private Date endDate;
  // private Float totalTime;
  // private Float startTime;
  // private Float endTime;
  // private List<String> hashtags;
}
