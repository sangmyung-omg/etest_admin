package com.tmax.eTest.Comment.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmax.eTest.Comment.dto.CommentDTO;
import com.tmax.eTest.Comment.dto.CommentMapDTO;
import com.tmax.eTest.Common.model.comment.CommentInfo;
import com.tmax.eTest.Common.repository.comment.CommentRepo;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CommentService {
	
	@Autowired
	CommentRepo commentRepo;
	
	public CommentMapDTO getAllComment()
	{
		CommentMapDTO result = new CommentMapDTO();
		
		List<CommentInfo> allComment = commentRepo.findAll();
		
		for(CommentInfo comment : allComment)
		{
			CommentDTO resDTO = new CommentDTO(comment);
			
			result.putComment(resDTO);
		}
		
		return result;
	}

	public int saveComment(List<CommentDTO> commentList) {
		// TODO Auto-generated method stub
		List<CommentInfo> modelList = new ArrayList<>();
		
		for(CommentDTO comment : commentList)
		{
			modelList.add(comment.toEntity());
		}
		
		return commentRepo.saveAll(modelList).size();
	}

}
