package com.tmax.eTest.TestStudio.dto.problems.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteProblemDTOIn {
	private String userID;
	//Long
	private String probID;
	//
}
