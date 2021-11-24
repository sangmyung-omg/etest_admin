package com.tmax.eTest.Comment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.tmax.eTest.Comment.util.CommentUtil;
import com.tmax.eTest.Common.model.comment.CommentVersionInfo;
import com.tmax.eTest.Common.repository.comment.CommentVersionRepo;

@Service

public class CommentVersionService {

	@Autowired
	CommentVersionRepo versionRepo;
	
	@Autowired
	CommentUtil util;
	
	public boolean isExistVersion(String versionName)
	{
		return versionRepo.existsById(versionName);
	}
	
	public boolean isSelectedVersion(String versionName)
	{
		if(versionRepo.existsById(versionName))
			return versionRepo.findById(versionName).get().getIsSelected() == 1;
		else
			return false;
	}
	
	public String getSelectedVersion()
	{
		String result = null;
		
		Optional<CommentVersionInfo> opt = versionRepo.findByIsSelected(1); 
		
		if(opt.isPresent())
			result = opt.get().getVersionName();
		
		return result;
	}
	
	public boolean changeActivateVersion(String versionName)
	{
		boolean result = false;
		
		List<CommentVersionInfo> versionList = versionRepo.findAll();
		List<CommentVersionInfo> newVersionList = new ArrayList<>(); 
		
		for(CommentVersionInfo info : versionList)
		{
			if(info.getVersionName().equals(versionName))
			{
				info.setIsSelected(1);
				result = true;
			}
			else
				info.setIsSelected(0);
			newVersionList.add(info);
		}
		
		if(result)
		{
			versionRepo.saveAll(newVersionList);
			util.putCommentToUserBackend();
		}
		
		return result;
	}
	
	public boolean deleteByVersion(String versionName)
	{
		versionRepo.deleteById(versionName);
		
		return true;
	}
	
	public boolean makeVersion(String versionName)
	{
		CommentVersionInfo info = CommentVersionInfo.builder()
				.versionName(versionName)
				.isSelected(0)
				.build();
		
		versionRepo.save(info);
		
		return true;
	}
}
