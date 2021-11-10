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
	private Integer index;
	private String commentName;
	private String commentText;
	private String ruleText;
	
	
	public CommentDTO(CommentGI model)	{
		this.versionName = model.getVersionName();
		this.index = model.getIndex();
		this.commentName = model.getCommentName();
		this.commentText = model.getCommentText();
		this.ruleText = model.getRuleText();
	}
	
	public CommentDTO(CommentInvest model)	{
		this.versionName = model.getVersionName();
		this.index = model.getIndex();
		this.commentName = model.getCommentName();
		this.commentText = model.getCommentText();
		this.ruleText = model.getRuleText();
	}
	
	public CommentDTO(CommentKnowledge model)	{
		this.versionName = model.getVersionName();
		this.index = model.getIndex();
		this.commentName = model.getCommentName();
		this.commentText = model.getCommentText();
		this.ruleText = model.getRuleText();
	}
	
	public CommentDTO(CommentRisk model)	{
		this.versionName = model.getVersionName();
		this.index = model.getIndex();
		this.commentName = model.getCommentName();
		this.commentText = model.getCommentText();
		this.ruleText = model.getRuleText();
	}
}
