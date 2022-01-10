package com.tmax.eTest.Contents.controller;

import com.tmax.eTest.Contents.service.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ImageController {

  @Autowired
  private ImageService imageService;

  @GetMapping("/image/thumbnail")
  public ResponseEntity<Object> getThumbnail(@RequestParam("url") String url) {
    log.info("---getThumbnail---");
    return new ResponseEntity<>(imageService.getThumbnail(url), HttpStatus.OK);
  }
}
