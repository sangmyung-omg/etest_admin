package com.tmax.eTest.Common.model.comment;

import java.io.Serializable;

import lombok.Data;

@Data
public class CommentKey implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String versionName;
	private Integer index;
}
