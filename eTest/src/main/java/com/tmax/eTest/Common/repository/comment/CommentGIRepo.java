package com.tmax.eTest.Common.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tmax.eTest.Common.model.comment.CommentGI;
import com.tmax.eTest.Common.model.comment.CommentKey;

public interface CommentGIRepo extends JpaRepository<CommentGI, CommentKey>{

}
