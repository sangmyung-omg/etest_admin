package com.tmax.eTest.Comment.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmax.eTest.Comment.util.CommentUtil.CommentType;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
@NoArgsConstructor
public class CommentMapDTO {
	Map<String, List<CommentDTO>> gi = new HashMap<>();
	Map<String, List<CommentDTO>> risk = new HashMap<>();
	Map<String, List<CommentDTO>> invest = new HashMap<>();
	Map<String, List<CommentDTO>> knowledge = new HashMap<>();
	
	public boolean putComment(CommentDTO dto)
	{
		List<CommentDTO> temp = new ArrayList<>();
		
		temp.add(dto);
		
		if(CommentType.GI.toString().equals(dto.getCommentType()))
		{
			if(gi.containsKey(dto.getVersionName()))
			{
				gi.get(dto.getVersionName()).add(dto);
			}
			else
			{
				gi.put(dto.getVersionName(), temp);
			}
		}
		else if(CommentType.RISK.toString().equals(dto.getCommentType()))
		{
			if(risk.containsKey(dto.getVersionName()))
			{
				risk.get(dto.getVersionName()).add(dto);
			}
			else
			{
				risk.put(dto.getVersionName(), temp);
			}
		}
		else if(CommentType.INVEST.toString().equals(dto.getCommentType()))
		{
			if(invest.containsKey(dto.getVersionName()))
			{
				invest.get(dto.getVersionName()).add(dto);
			}
			else
			{
				invest.put(dto.getVersionName(), temp);
			}
		}
		else if(CommentType.KNOWLEDGE.toString().equals(dto.getCommentType()))
		{
			if(knowledge.containsKey(dto.getVersionName()))
			{
				knowledge.get(dto.getVersionName()).add(dto);
			}
			else
			{
				knowledge.put(dto.getVersionName(), temp);
			}
		}
		else
			return false;
		
		return true;
	}
}
