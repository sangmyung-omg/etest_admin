package com.tmax.eTest.Common.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tmax.eTest.Common.model.comment.CommentKey;
import com.tmax.eTest.Common.model.comment.CommentRisk;

public interface CommentRiskRepo extends JpaRepository<CommentRisk, CommentKey>{

}
