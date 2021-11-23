package com.tmax.eTest.Common.model.video;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoCurriculum {
  @Id
  private Long curriculumId;
  private String subject;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "videoCurriculum", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Video> videos = new LinkedHashSet<Video>();
}
