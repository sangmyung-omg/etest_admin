package com.tmax.eTest.Common.repository.video;

import com.tmax.eTest.Common.model.video.VideoCurriculum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoCurriculumRepository extends JpaRepository<VideoCurriculum, Long> {

  @Query(value = "select * from video_curriculum where subject = (select code_name from meta_code_master where meta_code_id = ?1)", nativeQuery = true)
  VideoCurriculum findByCodeId(String codeId);
}
