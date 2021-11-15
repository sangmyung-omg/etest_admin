package com.tmax.eTest.Comment.dto;

import com.tmax.eTest.Common.model.comment.CommentGI;
import com.tmax.eTest.Common.model.comment.CommentInvest;
import com.tmax.eTest.Common.model.comment.CommentKnowledge;
import com.tmax.eTest.Common.model.comment.CommentRisk;

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
	private String commentName;
	private String commentText;
	private String ruleText;
	
	
	public CommentDTO(CommentGI model)	{
		this.versionName = model.getVersionName();
		this.seqNum = model.getSeqNum();
		this.commentName = model.getCommentName();
		this.commentText = model.getCommentText();
		this.ruleText = model.getRuleText();
	}
	
	public CommentDTO(CommentInvest model)	{
		this.versionName = model.getVersionName();
		this.seqNum = model.getSeqNum();
		this.commentName = model.getCommentName();
		this.commentText = model.getCommentText();
		this.ruleText = model.getRuleText();
	}
	
	public CommentDTO(CommentKnowledge model)	{
		this.versionName = model.getVersionName();
		this.seqNum = model.getSeqNum();
		this.commentName = model.getCommentName();
		this.commentText = model.getCommentText();
		this.ruleText = model.getRuleText();
	}
	
	public CommentDTO(CommentRisk model)	{
		this.versionName = model.getVersionName();
		this.seqNum = model.getSeqNum();
		this.commentName = model.getCommentName();
		this.commentText = model.getCommentText();
		this.ruleText = model.getRuleText();
	}
	
	public CommentGI toGIEntity()
	{
		CommentGI result = CommentGI.builder()
				.versionName(versionName)
				.seqNum(seqNum)
				.commentName(commentName)
				.commentName(commentName)
				.ruleText(ruleText)
				.build();
		
		return result;
	}
	
	public CommentRisk toRiskEntity()
	{
		CommentRisk result = CommentRisk.builder()
				.versionName(versionName)
				.seqNum(seqNum)
				.commentName(commentName)
				.commentName(commentName)
				.ruleText(ruleText)
				.build();
		
		return result;
	}
	
	public CommentKnowledge toKnowledgeEntity()
	{
		CommentKnowledge result = CommentKnowledge.builder()
				.versionName(versionName)
				.seqNum(seqNum)
				.commentName(commentName)
				.commentName(commentName)
				.ruleText(ruleText)
				.build();
		
		return result;
	}
	
	public CommentInvest toInvestEntity()
	{
		CommentInvest result = CommentInvest.builder()
				.versionName(versionName)
				.seqNum(seqNum)
				.commentName(commentName)
				.commentName(commentName)
				.ruleText(ruleText)
				.build();
		
		return result;
	}
}
