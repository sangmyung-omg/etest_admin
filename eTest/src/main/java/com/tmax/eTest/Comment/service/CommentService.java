package com.tmax.eTest.Comment.service;

import java.util.List;
import java.util.Map;

import com.tmax.eTest.Comment.dto.CommentDTO;

public interface CommentService {

	public Map<String, List<CommentDTO>> getAllComment();
	public boolean saveComment(List<CommentDTO> commentList);
	
}
