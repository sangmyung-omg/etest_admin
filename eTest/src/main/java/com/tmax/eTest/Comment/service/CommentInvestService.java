package com.tmax.eTest.Comment.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmax.eTest.Comment.dto.CommentDTO;
import com.tmax.eTest.Common.model.comment.CommentInvest;
import com.tmax.eTest.Common.repository.comment.CommentInvestRepo;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CommentInvestService {
	@Autowired
	CommentInvestRepo commentRepo;
	
	public Map<String, List<CommentDTO>> getAllComment()
	{
		Map<String, List<CommentDTO>> result = new HashMap<>();
		
		List<CommentInvest> allComment = commentRepo.findAll();
		
		for(CommentInvest comment : allComment)
		{
			CommentDTO resDTO = new CommentDTO(comment);
			
			if(result.containsKey(resDTO.getVersionName()))
			{
				result.get(resDTO.getVersionName()).add(resDTO);
			}
			else
			{
				List<CommentDTO> resList = new ArrayList<>();
				resList.add(resDTO);
				result.put(resDTO.getVersionName(), resList);
			}
		}
		
		return result;
	}
}
