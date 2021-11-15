package com.tmax.eTest.Common.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tmax.eTest.Common.model.comment.CommentGI;
import com.tmax.eTest.Common.model.comment.CommentKey;

@Repository
public interface CommentGIRepo extends JpaRepository<CommentGI, CommentKey>{

}
