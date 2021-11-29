package com.tmax.eTest.Comment.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@PropertySource("classpath:push-admin-config.properties")
public class CommentUtil {
	
    @Value("${backend.base.uri}")
    String userBackendUri;
	
	public void putCommentToUserBackend()
	{
		ResponseSpec response = WebClient.create().put().uri(userBackendUri + "/report/diagnosis/comment").retrieve();
		
		log.info("put to " + userBackendUri + "/report/diagnosis/comment");
		if(response != null)
			log.info(response.bodyToMono(Boolean.class).block());
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
