package com.tmax.eTest.Comment.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmax.eTest.Comment.dto.CommentDTO;
import com.tmax.eTest.Comment.service.CommentService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/comment")
public class CommentController {
	
	@Autowired
	CommentService commentService;
	
	@GetMapping(value="", produces = "application/json; charset=utf-8")
	public ResponseEntity<?> readComment(
			HttpServletRequest request) throws Exception{
		
		return ResponseEntity.ok(commentService.getAllComment());
	}
	
	@PostMapping(value="", produces = "application/json; charset=utf-8")
	public ResponseEntity<?> saveComment(
			HttpServletRequest request,
			@RequestBody List<CommentDTO> commentList) throws Exception{
		
		int savedListSize = commentService.saveComment(commentList);
		
		if(savedListSize == commentList.size())
			return ResponseEntity.ok(savedListSize);
		else
			return ResponseEntity.internalServerError().body("Succeed save comment num = "+ savedListSize);
	}
	
	@PutMapping(value="/activate/{commentType}/{versionName}", produces = "application/json; charset=utf-8")
	public ResponseEntity<?> changeActivateVersion(
			HttpServletRequest request,
			@PathVariable("versionName") String versionName) throws Exception{
	
//		if(!commentService.checkVersionName(versionName))
//			return ResponseEntity.internalServerError().body("Check versionName. Not Invalid. Now versionName is "
//					+ versionName);
	
		return ResponseEntity.ok(true);
	}
}
