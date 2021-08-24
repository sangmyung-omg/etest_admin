package com.tmax.eTest.TestStudio.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmax.eTest.Common.model.problem.DiagnosisProblem;
import com.tmax.eTest.TestStudio.dto.SelfProblemListDTO;
import com.tmax.eTest.TestStudio.repository.SelfRepository;

import lombok.RequiredArgsConstructor;

@Controller
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SelfController {

	private final SelfRepository selfRepository;

	@GetMapping("test-studio/problems/self")
	@ResponseBody
	public ResponseEntity<List<SelfProblemListDTO>> SelfProblemList(@RequestParam(value = "curriculumId") Integer curriculumId) {
		try {
			List<DiagnosisProblem> query = selfRepository.findByCurriculum_CurriculumId(curriculumId);
			List<SelfProblemListDTO> body = query.stream()
					.map(m -> new SelfProblemListDTO(m.getProbId(), m.getOrderNum(), m.getProblem().getDifficulty()))
					.collect(Collectors.toList());
			return new ResponseEntity<>(body, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}