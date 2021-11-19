package com.tmax.eTest.KdbStudio.controller;

import java.util.List;

import com.tmax.eTest.Common.model.uk.UkDescriptionVersion;
import com.tmax.eTest.KdbStudio.dto.UkUpdateDTO;
import com.tmax.eTest.KdbStudio.service.UkService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UkController {
    
    @Autowired
    UkService ukService;

    @GetMapping(name = "/uk", produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> getUkInfo(@RequestParam Integer versionId,
                                                @RequestParam List<String> part,
                                                @RequestParam Integer page,
                                                @RequestParam Integer size) {

        log.info("> Getting UKs for version : " + versionId + ". part = " + part.toString() + ", page = " + Integer.toString(page) + ", size = " + Integer.toString(size));
        List<UkDescriptionVersion> ukList = ukService.getAllUkInfoForVersion(versionId);

        return new ResponseEntity<>(ukList, HttpStatus.OK);
    }

    @PutMapping(name = "/uk", produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> updateUkInfo(@RequestParam Integer ukId, @RequestParam Integer versionId,
                                                    @RequestBody UkUpdateDTO inputBody) {
        log.info("> Update Uk Info. ukId : " + Integer.toString(ukId) + ", versionId : " + Integer.toString(versionId) + ", inputBody : " + inputBody.toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
