package com.tmax.eTest.Common.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tmax.eTest.Common.model.comment.CommentVersionInfo;

@Repository
public interface CommentSelectedInfoRepo extends JpaRepository<CommentVersionInfo, Integer> {

}
