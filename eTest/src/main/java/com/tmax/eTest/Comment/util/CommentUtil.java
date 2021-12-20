package com.tmax.eTest.Comment.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@PropertySource("classpath:application.properties")
public class CommentUtil {
	
	private List<String> userBackendIP = new ArrayList<>();
	private List<String> userBackendPort = new ArrayList<>();
	
    @Autowired
    public CommentUtil(
    		@Value("${etest.user.backend.host}") List<String> ipList, 
			@Value("${etest.user.backend.port}") List<String> portList)
    {
    	for(String ip : ipList)
    	{
    		log.info("User Backend ip = "+ip);
    		userBackendIP.add(ip);
    	}
    	
    	for(String port : portList)
    	{
    		log.info("User Backend Port = "+port);
    		userBackendPort.add(port);
    	}
    	
    }	

    
    
	public void putCommentToUserBackend()
	{
		for(int i = 0 ; i < userBackendIP.size(); i++)
		{
			String ip = userBackendIP.get(i);
			String port = (userBackendPort.size() > i) ? userBackendPort.get(i)
					:(userBackendPort.size() > 0) ? userBackendPort.get(0)
					:"8080";
			
	    	String userBackCommentRefreshURI = 
	    			String.format("http://%s:%s/report/diagnosis/comment", 
	    			ip, 
	    			port);
			ResponseSpec response = WebClient.create().put().uri(userBackCommentRefreshURI).retrieve();
			
			log.info("put to " + userBackCommentRefreshURI);
			if(response != null)
				log.info(response.bodyToMono(Boolean.class).block().toString());
		}
	}
	
	public enum CommentType{
		ALL("all"),
		KNOWLEDGE("knowledge"),
		RISK("risk"),
		INVEST("invest"),
		GI("gi");
		
		private String value;
		
		private CommentType(String value) {
			this.value = value;
		}
		
		public final String toString() {
			return this.value;
		}
	}
	public static CommentType getCommentTypeFromSeqNum(int seqNum)
	{
		if(seqNum < 3)
			return CommentType.GI;
		else if(seqNum < 70)
			return CommentType.RISK;
		else if(seqNum < 112)
			return CommentType.INVEST;
		else
			return CommentType.KNOWLEDGE;
	}
}
