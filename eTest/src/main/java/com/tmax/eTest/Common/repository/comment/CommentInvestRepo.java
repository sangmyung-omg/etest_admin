package com.tmax.eTest.Common.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tmax.eTest.Common.model.comment.CommentInvest;
import com.tmax.eTest.Common.model.comment.CommentKey;


public interface CommentInvestRepo extends JpaRepository<CommentInvest, CommentKey>{

}
