package com.tmax.eTest.Comment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tmax.eTest.Comment.dto.CommentDTO;
import com.tmax.eTest.Comment.dto.CommentMapDTO;
import com.tmax.eTest.Comment.service.CommentGIService;
import com.tmax.eTest.Comment.service.CommentInvestService;
import com.tmax.eTest.Comment.service.CommentKnowledgeService;
import com.tmax.eTest.Comment.service.CommentRiskService;
import com.tmax.eTest.Comment.util.CommentUtil.CommentUnit;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/comment")
public class CommentController {
	
	@Autowired
	CommentGIService giService;
	@Autowired
	CommentInvestService investService;
	@Autowired
	CommentKnowledgeService knowledgeService;
	@Autowired
	CommentRiskService riskService;
	
	
	@GetMapping(value="/{commentType}", produces = "application/json; charset=utf-8")
	public CommentMapDTO readComment(
			HttpServletRequest request,
			@PathVariable("commentType") String commentType) throws Exception{
		
		CommentMapDTO result = new CommentMapDTO();
	
		if(commentType.equals(CommentUnit.GI.toString()) 
			|| commentType.equals(CommentUnit.ALL.toString()))
		{
			result.setGi(giService.getAllComment());
		}
		
		if(commentType.equals(CommentUnit.RISK.toString())
			|| commentType.equals(CommentUnit.ALL.toString()))
		{
			result.setRisk(riskService.getAllComment());
		}
		
		if(commentType.equals(CommentUnit.INVEST.toString())
			|| commentType.equals(CommentUnit.ALL.toString()))
		{
			result.setInvest(investService.getAllComment());
		}
		
		if(commentType.equals(CommentUnit.KNOWLEDGE.toString())
			|| commentType.equals(CommentUnit.ALL.toString()))
		{
			result.setKnowledge(knowledgeService.getAllComment());
		}
		
		return result;
	}
	
	@PutMapping(value="", produces = "application/json; charset=utf-8")
	public boolean saveComment(
			HttpServletRequest request,
			@RequestParam("commentType") String commentType) throws Exception{
		
		return true;
	}
}
