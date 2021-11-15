package com.tmax.eTest.Comment.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentMapDTO {
	Map<String, List<CommentDTO>> gi = new HashMap<>();
	Map<String, List<CommentDTO>> risk = new HashMap<>();
	Map<String, List<CommentDTO>> invest = new HashMap<>();
	Map<String, List<CommentDTO>> knowledge = new HashMap<>();
}
