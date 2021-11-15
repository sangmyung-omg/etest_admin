package com.tmax.eTest.Common.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tmax.eTest.Common.model.comment.CommentKey;
import com.tmax.eTest.Common.model.comment.CommentKnowledge;

@Repository
public interface CommentKnowledgeRepo extends JpaRepository<CommentKnowledge, CommentKey>{

}
