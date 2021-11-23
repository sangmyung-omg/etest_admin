package com.tmax.eTest.Contents.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.tmax.eTest.Contents.dto.CodeCreateDTO;
import com.tmax.eTest.Contents.service.CodeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CodeController {

  @Autowired
  private CodeService codeService;

  @GetMapping("/codes")
  public ResponseEntity<Object> getCodeList(@RequestParam(value = "domain", required = true) String domain)
      throws IOException {
    log.info("---getCodeList---");
    return new ResponseEntity<>(codeService.getCodeList(domain), HttpStatus.OK);
  }

  @PostMapping("/codes")
  public ResponseEntity<Object> createCode(@RequestBody CodeCreateDTO codeCreateDTO) {
    log.info("---createCode---");
    return new ResponseEntity<>(codeService.createCode(codeCreateDTO), HttpStatus.OK);
  }

  @PutMapping("/codes/{codeId}")
  public ResponseEntity<Object> updateCode(@PathVariable(value = "codeId") String codeId,
      @RequestBody CodeCreateDTO codeCreateDTO) throws UnsupportedEncodingException {
    log.info("---updateCode---");
    return new ResponseEntity<>(codeService.updateCode(codeId, codeCreateDTO), HttpStatus.OK);
  }
}
