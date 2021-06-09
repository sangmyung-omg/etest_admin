package com.tmax.eTest.Contents.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tmax.eTest.Contents.dto.problem.ProblemDTO;
import com.tmax.eTest.Contents.exception.problem.NoDataException;
import com.tmax.eTest.Contents.model.ProblemChoice;
import com.tmax.eTest.Contents.model.TestProblem;
import com.tmax.eTest.Contents.model.DiagnosisProblem;
import com.tmax.eTest.Contents.model.Problem;
import com.tmax.eTest.Contents.repository.DiagnosisProblemRepository;
import com.tmax.eTest.Contents.repository.ProblemChoiceRepository;
import com.tmax.eTest.Contents.repository.ProblemRepository;
import com.tmax.eTest.Contents.repository.ProblemUKRelRepository;
import com.tmax.eTest.Contents.repository.TestProblemRepository;
import com.tmax.eTest.Test.repository.UkRepository;


@Service
public class ProblemServices {
	@Autowired
	DiagnosisProblemRepository diagProbRepo;
	
	@Autowired
	ProblemChoiceRepository probChoiceRepo;
	
	@Autowired
	ProblemRepository problemRepo;
	
	@Autowired
	ProblemUKRelRepository probUKRelRepo;
	
	@Autowired
	TestProblemRepository testProblemRepo;
	
	@Autowired
	UkRepository uKMasterRepo;
	
	public ProblemDTO getProblem(long problemID) throws Exception{
		ProblemDTO output;
		Optional<Problem> problemOpt = problemRepo.findById(problemID);
		
		if(problemOpt.isPresent()) {
			Problem problem = problemOpt.get();
			Map<Long, String> choices = new HashMap<Long, String>();
			String type = problem.getAnswerType();
			String questionJsonString = problem.getQuestion();
			JsonObject questionJson= JsonParser.parseString(questionJsonString).getAsJsonObject();

			//asString 쓰면 null인경우에 에러남
			String question = questionJson.get("question").toString().replaceAll("\"", "");
			String passage = questionJson.get("passage").toString().replaceAll("\"", "");
			String preface = questionJson.get("preface").toString().replaceAll("\"", "");

			String difficulty = problem.getDifficulty();
			
			List<ProblemChoice> choiceList = probChoiceRepo.findAllByProbID(problem);
			for(ProblemChoice choice:choiceList) {
				choices.put(choice.getChoiceNum(), choice.getText());
			}
			output = new ProblemDTO(type, question, passage, preface, difficulty, choices);
			
		}else {
			throw new NoDataException(problemID);
		}
		
		return output;
	}

	public List<Long> getTestProblem(int setNum, int index) throws Exception{
		List<Long> output = new ArrayList<Long>(); 

		index = Math.max(0, index);
		List<TestProblem> testProblems = testProblemRepo.findSetProblems(setNum, index);
	
		if(testProblems.size()==0) {
			throw new NoDataException(setNum);
		}else {
			for(TestProblem t : testProblems) {
				output.add(t.getProbID());
			}
		}
		return output;
	}
	public List<Long> getDiagnosisProblem(int setNum) throws Exception{
		List<Long> output = new ArrayList<Long>(); 

		List<DiagnosisProblem> diagnosisProblems = diagProbRepo.findDiagnosisProblems(setNum);
		if(diagnosisProblems.size()==0) {
			throw new NoDataException(setNum);
		}else {
			for(DiagnosisProblem d : diagnosisProblems) {
				output.add(d.getProbID());
			}
		}
		return output;
	}
}

