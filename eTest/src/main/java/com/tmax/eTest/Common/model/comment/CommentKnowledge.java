package com.tmax.eTest.Common.model.comment;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@IdClass(CommentKey.class)
@Table(name="COMMENT_KNOWLEDGE")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentKnowledge {
	@Id
	private String versionName;
	@Id
	private Integer index;
	private String commentName;
	private String commentText;
	private String ruleText;
}
