package com.tmax.eTest.Comment.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tmax.eTest.Comment.dto.CommentDTO;
import com.tmax.eTest.Comment.dto.CommentMapDTO;
import com.tmax.eTest.Comment.service.CommentService;
import com.tmax.eTest.Comment.service.CommentVersionService;
import com.tmax.eTest.Comment.util.CommentUtil;
import com.tmax.eTest.Comment.util.VersionGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/comment")
public class CommentController {
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	CommentVersionService versionService;
	
	@Autowired
	CommentUtil util;

	
	@GetMapping(value="", produces = "application/json; charset=utf-8")
	public ResponseEntity<?> readComment(
			HttpServletRequest request) throws Exception{
		
		CommentMapDTO result =  commentService.getAllComment();
		String selectedVersion = versionService.getSelectedVersion();
		
		if(selectedVersion != null)
		{
			result.setSelectedVersion(selectedVersion);
			return ResponseEntity.ok(result);
		}
		else
		{
			versionService.changeActivateVersion("default_version");
			return ResponseEntity.internalServerError().body("Selected Version error. Selected Version will be change 'default_version'");
		}
	}
	
	@PostMapping(value="", produces = "application/json; charset=utf-8")
	public ResponseEntity<?> saveComment(
			HttpServletRequest request,
			@RequestBody List<CommentDTO> commentList) throws Exception{
		
		int savedListSize = commentService.saveComment(commentList);
		
		if(commentList.size() == 0)
			return ResponseEntity.internalServerError().body("Please check input. CommentList size is 0.");
		else if(savedListSize == commentList.size() && savedListSize != 0)
		{
			util.putCommentToUserBackend();
			return ResponseEntity.ok(savedListSize);	
		}
		else
			return ResponseEntity.internalServerError().body("Save fail. Succeed save comment num = "+ savedListSize);
	}
	
	@PutMapping(value="/activateVersion", produces = "application/json; charset=utf-8")
	public ResponseEntity<?> changeActivateVersion(
			HttpServletRequest request,
			@RequestParam("versionName") String versionName) throws Exception{
	
		if(versionService.changeActivateVersion(versionName))
		{
			util.putCommentToUserBackend();
			return ResponseEntity.ok(true);
		}
		else
			return ResponseEntity.internalServerError().body("Check versionName. Not Invalid. Now versionName is "
					+ versionName);
		
	}
	
	@PutMapping(value="/version/copy", produces = "application/json; charset=utf-8")
	public ResponseEntity<?> copyVersion(
			HttpServletRequest request,
			@RequestParam("prevVersionName") String prevVersionName) throws Exception{

		if(!versionService.isExistVersion(prevVersionName))
			return ResponseEntity.internalServerError().body("Check prevVersionName. Not exists. prevVersionName is "
					+ prevVersionName);
		
		String newVersionName = VersionGenerator.getVersionName();
		
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
		
		return ResponseEntity.ok(newVersionName);
	}
	
	@PutMapping(value="/version", produces = "application/json; charset=utf-8")
	public ResponseEntity<?> makeNewCommentVersion(
			HttpServletRequest request) throws Exception{

		String newVersionName = VersionGenerator.getVersionName();
		
		if(versionService.isExistVersion(newVersionName))
			return ResponseEntity.internalServerError().body("Check newVersionName. It already exists. newVersionName is "
					+ newVersionName);
		
		if(!commentService.makeDefaultComment(newVersionName)
			|| !versionService.makeVersion(newVersionName))
		{
			// rollback
			commentService.deleteCommentByVersion(newVersionName);
			versionService.deleteByVersion(newVersionName);
			
			return ResponseEntity.internalServerError().body("Something wrong! in make comment with new version name! "
					+ newVersionName);
		}
	
		
		return ResponseEntity.ok(newVersionName);
	}
	


	@DeleteMapping(value="/version", produces = "application/json; charset=utf-8")
	public ResponseEntity<?> deleteVersion(
			HttpServletRequest request,
			@RequestParam("versionName") String versionName) throws Exception{
	
		if(!versionService.isExistVersion(versionName) 
			|| versionService.isSelectedVersion(versionName)
			|| versionName.equals("default_version"))
			return ResponseEntity.internalServerError().body("Check versionName. Not Invalid or selected. versionName is "
					+ versionName);
		
		boolean commentResult = commentService.deleteCommentByVersion(versionName);
		boolean versionResult = versionService.deleteByVersion(versionName);
		
		if(!commentResult || !versionResult)
			return ResponseEntity.internalServerError().body("Check versionName. Not Invalid. versionName is "
					+ versionName);
		
		return ResponseEntity.ok(true);
	}
	
	
	
}
