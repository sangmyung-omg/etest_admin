package com.tmax.eTest.KdbStudio.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmax.eTest.Common.model.uk.VersionMaster;
import com.tmax.eTest.KdbStudio.dto.VersionCreateInputDTO;
import com.tmax.eTest.KdbStudio.service.VersionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class VersionController {
    @Autowired
    VersionService versionService;

    @GetMapping(value="/version", produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> getVersionInfo() {
        log.info("> Getting all version infos");
        Map<String, Object> map = new HashMap<String, Object>();

        List<VersionMaster> versionList = versionService.getAllVersionInfo();

        map.put("message", "Successfully returned version informations.");
        map.put("verionList", versionList);
        
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping(value="/version", produces = "application/json; charset-utf-8")
    public ResponseEntity<Object> createNewVersion(@RequestBody VersionCreateInputDTO inputBody) {
        log.info("> Creating new version. input = " + inputBody.toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value="/version/{versionId}", produces = "application/json; charset-utf-8")
    public ResponseEntity<Object> updateVersionInfo(@PathVariable Integer versionId, @RequestBody String versionName) {
        log.info("> Update version info. versionId = " + Integer.toString(versionId) + ", versionName = " + versionName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value="/version/{versionId}", produces = "application/json; charset-utf-8")
    public ResponseEntity<Object> copyVersionInfo(@PathVariable Integer versionId, @RequestBody VersionCreateInputDTO inputBody) {
        log.info("> Copy and paste version info. versionId = " + Integer.toString(versionId) + ", inputBody = " + inputBody.toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value="/version/{versionId}", produces = "application/json; charset-utf-8")
    public ResponseEntity<Object> deleteVersion(@PathVariable Integer versionId) {
        log.info("> Delete version. versionId = " + Integer.toString(versionId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value="/version/default/{versionId}", produces = "application/json; charset-utf-8")
    public ResponseEntity<Object> applyDefaultVersion(@PathVariable Integer versionId) {
        log.info("> Apply default version. versionId = " + Integer.toString(versionId));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
