package com.tmax.eTest.Common.model.video;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.tmax.eTest.Common.model.uk.UkMaster;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(VideoUkRelId.class)
public class VideoUkRel {
  @Id
  private String videoId;
  @Id
  private Long ukId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "videoId", insertable = false, updatable = false)
  private Video video;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "ukId", insertable = false, updatable = false)
  private UkMaster ukMaster;
}
