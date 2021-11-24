package com.tmax.eTest.Comment.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@PropertySource("classpath:push-admin-config.properties")  
public class CommentUtil {
	
	@Value("${backend.base.uri}")
    static String userBackendUri;
	
	public static void putCommentToUserBackend()
	{
		WebClient.create().put().uri(userBackendUri + "/report/diagnosis/comment");
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
