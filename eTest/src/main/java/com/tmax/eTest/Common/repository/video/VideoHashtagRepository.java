package com.tmax.eTest.Common.repository.video;

import com.tmax.eTest.Common.model.video.VideoHashtag;
import com.tmax.eTest.Common.model.video.VideoHashtagId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoHashtagRepository extends JpaRepository<VideoHashtag, VideoHashtagId> {

}
