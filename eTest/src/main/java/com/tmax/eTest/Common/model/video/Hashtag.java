package com.tmax.eTest.Common.model.video;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Hashtag {
  @Id
  @SequenceGenerator(name = "HASHTAG_SEQ_GENERATOR", sequenceName = "HASHTAG_SEQ", allocationSize = 1, initialValue = 20000)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HASHTAG_SEQ_GENERATOR")
  private Long hashtagId;
  private String name;

  @Builder.Default
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "hashtag", cascade = CascadeType.ALL, orphanRemoval = false)
  private Set<VideoHashtag> videoHashtags = new LinkedHashSet<VideoHashtag>();

}
