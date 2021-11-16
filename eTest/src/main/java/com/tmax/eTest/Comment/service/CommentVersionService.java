package com.tmax.eTest.Comment.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmax.eTest.Common.model.comment.CommentVersionInfo;
import com.tmax.eTest.Common.repository.comment.CommentVersionRepo;

@Service
public class CommentVersionService {

	@Autowired
	CommentVersionRepo versionRepo;
	
	public boolean isExistVersion(String versionName)
	{
		return versionRepo.existsById(versionName);
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
			versionRepo.saveAll(newVersionList);
		
		return result;
	}
}
