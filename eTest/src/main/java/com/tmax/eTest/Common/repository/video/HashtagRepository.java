package com.tmax.eTest.Common.repository.video;

import java.util.List;

import com.tmax.eTest.Common.model.video.Hashtag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
  List<Hashtag> findByNameIn(List<String> names);
}
