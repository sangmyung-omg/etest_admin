package com.tmax.eTest.Common.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tmax.eTest.Common.model.comment.CommentSelectedInfo;

@Repository
public interface CommentSelectedInfoRepo extends JpaRepository<CommentSelectedInfo, String> {

}
