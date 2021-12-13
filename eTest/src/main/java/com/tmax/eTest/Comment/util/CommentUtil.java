package com.tmax.eTest.Comment.util;

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
	
    private String userBackCommentRefreshURI = "http://220.90.208.217:8080/report/diagnosis/comment";
	
    @Autowired
    public CommentUtil(
    		@Value("${etest.user.backend.host}") String IP, 
			@Value("${etest.user.backend.port}") String PORT)
    {
    	log.info("User Backend URI = "+IP+":"+PORT);
    	
    	this.userBackCommentRefreshURI = String.format("http://%s:%s/report/diagnosis/comment", IP, PORT);
    }	

    
    
	public void putCommentToUserBackend()
	{
		ResponseSpec response = WebClient.create().put().uri(userBackCommentRefreshURI).retrieve();
		
		log.info("put to " + userBackCommentRefreshURI);
		if(response != null)
			log.info(response.bodyToMono(Boolean.class).block().toString());
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
