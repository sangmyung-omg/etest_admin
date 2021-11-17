package com.tmax.eTest.Comment.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tmax.eTest.Comment.dto.CommentDTO;
import com.tmax.eTest.Comment.service.CommentService;
import com.tmax.eTest.Comment.service.CommentVersionService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/comment")
public class CommentController {
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	CommentVersionService versionService;
	
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
	
	@PutMapping(value="/activateVersion", produces = "application/json; charset=utf-8")
	public ResponseEntity<?> changeActivateVersion(
			HttpServletRequest request,
			@RequestParam("versionName") String versionName) throws Exception{
	
		if(versionService.changeActivateVersion(versionName))
			return ResponseEntity.ok(true);
		else
			return ResponseEntity.internalServerError().body("Check versionName. Not Invalid. Now versionName is "
					+ versionName);
		
	}
	
	@PutMapping(value="/version", produces = "application/json; charset=utf-8")
	public ResponseEntity<?> copyOrCutVersion(
			HttpServletRequest request,
			@RequestParam("prevVersionName") String prevVersionName,
			@RequestParam("newVersionName") String newVersionName,
			@RequestParam("isKeepPrevVersion") Boolean isKeepPrevVersion) throws Exception{
	
		if(versionService.isExistVersion(newVersionName))
			return ResponseEntity.internalServerError().body("Check newVersionName. It already exists. newVersionName is "
					+ newVersionName);
		
		if( !versionService.isExistVersion(prevVersionName) 
			|| !commentService.copyComment(prevVersionName, newVersionName)
			|| !versionService.makeVersion(newVersionName))
		{
			// rollback
			commentService.deleteCommentByVersion(newVersionName);
			versionService.deleteByVersion(newVersionName);
			
			return ResponseEntity.internalServerError().body("Check prevVersionName. Not Invalid. prevVersionName is "
					+ prevVersionName);
		}
		
		if(!isKeepPrevVersion) // Copy? or Cut?
		{
			commentService.deleteCommentByVersion(prevVersionName);
			versionService.deleteByVersion(prevVersionName);
		}
		
		
		return ResponseEntity.ok(true);
	}

	@DeleteMapping(value="/version", produces = "application/json; charset=utf-8")
	public ResponseEntity<?> deleteVersion(
			HttpServletRequest request,
			@RequestParam("versionName") String versionName) throws Exception{
	
		if(!versionService.isExistVersion(versionName))
			return ResponseEntity.internalServerError().body("Check versionName. Not Invalid. versionName is "
					+ versionName);
		
		boolean commentResult = commentService.deleteCommentByVersion(versionName);
		boolean versionResult = versionService.deleteByVersion(versionName);
		
		if(!commentResult || !versionResult)
			return ResponseEntity.internalServerError().body("Check versionName. Not Invalid. versionName is "
					+ versionName);
		
		return ResponseEntity.ok(true);
	}
	
	
	
}
