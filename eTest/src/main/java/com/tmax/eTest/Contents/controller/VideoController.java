package com.tmax.eTest.Contents.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.tmax.eTest.Contents.dto.SortType;
import com.tmax.eTest.Contents.dto.VideoCreateDTO;
import com.tmax.eTest.Contents.service.VideoService;
import com.tmax.eTest.Contents.util.CommonUtils;
import com.tmax.eTest.Contents.util.JWTUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class VideoController {

  @Autowired
  private VideoService videoService;

  @Autowired
  private CommonUtils commonUtils;

  @Autowired
  private JWTUtils jwtUtils;

  @GetMapping("/videos/curriculums")
  public ResponseEntity<Object> getVideoCurriculumList() {
    log.info("---getVideoCurriculumList---");
    return new ResponseEntity<>(videoService.getVideoCurriculumList(), HttpStatus.OK);
  }

  @GetMapping("/videos")
  public ResponseEntity<Object> getVideoList(@RequestParam(value = "curriculumId", required = false) Long curriculumId,
      @RequestParam(value = "sort", required = false, defaultValue = "DATE") SortType sort,
      @RequestParam(value = "keyword", required = false) String keyword,
      @RequestParam(value = "page", required = false, defaultValue = "-1") Integer page,
      @RequestParam(value = "size", required = false, defaultValue = "-1") Integer size, HttpServletRequest request)
      throws IOException {
    log.info("---getVideoList---");
    return new ResponseEntity<>(page == -1 ? videoService.getVideoList(curriculumId, sort, keyword)
        : videoService.getVideoList(curriculumId, sort, keyword, page, size), HttpStatus.OK);
  }

  @PostMapping("/videos")
  public ResponseEntity<Object> createVideo(@RequestBody VideoCreateDTO videoCreateDTO, HttpServletRequest request) {
    log.info("---createVideo---");
    return new ResponseEntity<>(videoService.createVideo(videoCreateDTO), HttpStatus.OK);
  }

  @GetMapping("/videos/size")
  public ResponseEntity<Object> getVideoSize(HttpServletRequest request) {
    log.info("---getVideoSize---");
    return new ResponseEntity<>(videoService.getVideoSize(), HttpStatus.OK);
  }

  @GetMapping("/videos/{video_id}")
  public ResponseEntity<Object> getVideo(@PathVariable("video_id") String videoId, HttpServletRequest request) {
    log.info("---getVideo---");
    return new ResponseEntity<>(videoService.getVideo(videoId), HttpStatus.OK);
  }

  @PostMapping("/videos/{video_id}")
  public ResponseEntity<Object> updateVideo(@PathVariable("video_id") String videoId,
      @RequestBody VideoCreateDTO videoCreateDTO, HttpServletRequest request) {
    log.info("---updateVideo---");
    return new ResponseEntity<>(videoService.updateVideo(videoId, videoCreateDTO), HttpStatus.OK);
  }

  @DeleteMapping("/videos/{video_id}")
  public ResponseEntity<Object> deleteVideo(@PathVariable("video_id") String videoId, HttpServletRequest request) {
    log.info("---deleteVideo---");
    return new ResponseEntity<>(videoService.deleteVideo(videoId), HttpStatus.OK);
  }

}
