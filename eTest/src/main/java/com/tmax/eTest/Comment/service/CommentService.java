package com.tmax.eTest.Comment.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmax.eTest.Comment.dto.CommentDTO;
import com.tmax.eTest.Comment.dto.CommentMapDTO;
import com.tmax.eTest.Common.model.comment.CommentInfo;
import com.tmax.eTest.Common.model.comment.CommentKey;
import com.tmax.eTest.Common.repository.comment.CommentRepo;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CommentService {
	
	@Autowired
	CommentRepo commentRepo;
	
	private final String DEFAULT_VERSION = "default_version";
	
	public CommentMapDTO getAllComment()
	{
		CommentMapDTO result = new CommentMapDTO();
		
		List<CommentInfo> allComment = commentRepo.findAll();
		
		// version sort.
		allComment.sort(Comparator.comparing(CommentInfo::getVersionName));
		
		
		for(CommentInfo comment : allComment)
		{
			if(!comment.getVersionName().equals(DEFAULT_VERSION))
			{
				result.putComment(new CommentDTO(comment));
			}
		}
		
		return result;
	}

	public int saveComment(List<CommentDTO> commentList) {
		// TODO Auto-generated method stub
		List<CommentInfo> modelList = new ArrayList<>();
		
		for(CommentDTO comment : commentList)
		{
			CommentKey key = new CommentKey(comment.getVersionName(), comment.getSeqNum());
			
			Optional<CommentInfo> opt = commentRepo.findById(key);
			
			if(opt.isPresent())
			{
				CommentInfo saveInfo = opt.get();
				saveInfo.setCommentText(comment.getCommentText());
				modelList.add(saveInfo);
			}
			else
				log.error("Error in save comment process. " + comment.toString());
			
		}
		
		log.info(modelList.toString());
		
		return commentRepo.saveAll(modelList).size();
	}
	
	public boolean makeDefaultComment(String newVersion)
	{
		boolean result = false;
		List<CommentInfo> list = commentRepo.findAllByVersionName(DEFAULT_VERSION);
		List<CommentInfo> saveList = new ArrayList<>();
		
		if(list.size() != 0)
		{
			for(CommentInfo info : list)
			{
				CommentInfo newInfo = CommentInfo.builder()
					.versionName(newVersion)
					.seqNum(info.getSeqNum())
					.commentText("")
					.commentType(info.getCommentType())
					.commentName(info.getCommentName())
					.ruleText(info.getRuleText())
					.build();
				
				saveList.add(newInfo);
			}
			
			commentRepo.saveAll(saveList);
			result = true;
		}
		
		return result;
	}
	
	public boolean copyComment(String prevVersion, String newVersion)
	{
		boolean result = false;
		
		List<CommentInfo> list = commentRepo.findAllByVersionName(prevVersion);
		List<CommentInfo> saveList = new ArrayList<>();
		
		if(list.size() != 0)
		{
			for(CommentInfo info : list)
			{
				CommentInfo newInfo = CommentInfo.builder()
					.versionName(newVersion)
					.seqNum(info.getSeqNum())
					.commentText(info.getCommentText())
					.commentType(info.getCommentType())
					.commentName(info.getCommentName())
					.ruleText(info.getRuleText())
					.build();
				
				saveList.add(newInfo);
			}
			
			commentRepo.saveAll(saveList);
			result = true;
		}
		
		return result;
	}

	public boolean deleteCommentByVersion(String versionName)
	{
		if(versionName.equals(DEFAULT_VERSION))
		{
			log.info("You can't delete 'default_version' in comment");
			return false;
		}
		else
			return commentRepo.deleteByVersionName(versionName) != 0;
	}
	
}
