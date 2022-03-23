package com.tmax.eTest.Comment.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tmax.eTest.Common.model.comment.CommentVersionInfo;
import com.tmax.eTest.Common.repository.comment.CommentVersionRepo;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class VersionGenerator {
	@Autowired
	CommentVersionRepo versionRepo;
	
	static int versionIdx = 0;
	
	static public String getVersionName()
	{
		String result = "Ver 1."+versionIdx;
		
		versionIdx++;
		
		return result;
	}
	
	@EventListener
    public void startUpGenerator(ApplicationStartedEvent event){
        List<CommentVersionInfo> set = versionRepo.findAll();
        initRoutine(set);
    }

    @Scheduled(fixedRate=60*60*1000)
    public void scheduledUpdater(){
        List<CommentVersionInfo> set = versionRepo.findAll();
        initRoutine(set);
    }

    private void initRoutine(List<CommentVersionInfo> set)
    {
    	for(CommentVersionInfo info : set)
    	{
    		String versionName = info.getVersionName();
    		
    		if(versionName.equals("default_version"))
    			continue;
    		
    		String[] versionSplit = versionName.split("\\.");
    		
    		if(versionSplit.length > 1)
    		{
    			try {
    				int versionNum = Integer.parseInt(versionSplit[1]);
    				if(versionNum >= versionIdx)
    					versionIdx = versionNum + 1;
    			}
    			catch(NumberFormatException e)
    			{
    				log.debug("VersionGenerator error in parseInt "+versionSplit[1]);
    			}
    		}
    	}
    	log.debug("Now New comment version name will be Ver 1."+versionIdx);
    }
}
