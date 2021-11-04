package com.tmax.eTest.Common.model.comment;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="COMMENT_SELECTED_INFO")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentSelectedInfo {
	@Id
	private String tableName;
	private String selected_version;
}
