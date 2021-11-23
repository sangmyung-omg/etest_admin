package com.tmax.eTest.Comment.dto;

import com.tmax.eTest.Common.model.comment.CommentInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
	private String versionName;
	private Integer seqNum;
	private String commentType;
	private String commentName;
	private String commentText;
	private String ruleText;
	
	
	public CommentDTO(CommentInfo model)	{
		this.versionName = model.getVersionName();
		this.seqNum = model.getSeqNum();
		this.commentName = model.getCommentName();
		this.commentText = model.getCommentText();
		this.ruleText = model.getRuleText();
		this.commentType = model.getCommentType();
	}
	
	public CommentInfo toEntity()
	{
		CommentInfo result = CommentInfo.builder()
				.versionName(versionName)
				.seqNum(seqNum)
				.commentType(commentType)
				.commentName(commentName)
				.commentText(commentText)
				.ruleText(ruleText)
				.build();
		
		return result;
	}
	
}
