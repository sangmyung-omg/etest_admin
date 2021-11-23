package com.tmax.eTest.TestStudio.dto.problems.base;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseProblemDTO {
	//Long
	private String probID;
	//
	private String answerType;
	private String question;
	private String solution;
	private String difficulty;
	private String category;
	private String imgSrc;
	private String timeRecommendation;
	private String creatorID;
	private ZonedDateTime createDate;
	private String validatorID;
	private ZonedDateTime validateDate;
	private String editorID;
	private ZonedDateTime editDate;
	private String source;
	private String intention;
	private String questionInitial;
	private String solutionInitial;
	
	// temp
	private String imgsOut;
//	private String darkImgsOut;
	//
	private List<String> imgSrcListIn;
	private List<String> imgToEditSrcListIn;
//	private List<String> darkImgListIn;
}
