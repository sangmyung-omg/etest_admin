package com.tmax.eTest.KdbStudio.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmax.eTest.Common.model.uk.VersionMaster;
import com.tmax.eTest.KdbStudio.dto.VersionCreateInputDTO;
import com.tmax.eTest.KdbStudio.dto.VersionGetOutputDTO;
import com.tmax.eTest.KdbStudio.dto.VersionUpdateInputDTO;
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

import javassist.NotFoundException;
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

        List<VersionGetOutputDTO> versionList = versionService.getAllVersionInfo();

        if (versionList == null) 
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        map.put("message", "Successfully returned version informations.");
        map.put("versionList", versionList);
        
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping(value="/version", produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> createNewVersion(@RequestBody VersionCreateInputDTO inputBody) {
        log.info("> Creating new version. input = " + inputBody.toString());
        Map<String, Object> map = new HashMap<String, Object>();

        // input check
        if (inputBody.getVersionName() == null || inputBody.getVersionName().equalsIgnoreCase("")) {
            map.put("error", "Version name is empty.");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        } else if (inputBody.getVersionNum() == null || inputBody.getVersionNum().equalsIgnoreCase("")) {
            map.put("error", "Version number is empty.");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        Long newVersionId = versionService.insertVersion(inputBody);

        if (newVersionId == -1) {
            map.put("message", "Version Num '" + inputBody.getVersionNum() + "' already exists. Please use new version number.");
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            map.put("message", "Successfully created new version.");
            map.put("versionId", newVersionId);
    
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @PutMapping(value="/version/{versionId}", produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> updateVersionInfo(@PathVariable Integer versionId, @RequestBody VersionUpdateInputDTO inputBody) {
        log.info("> Update version info. versionId = " + Integer.toString(versionId) + ", versionName = " + inputBody);
        Map<String, Object> map = new HashMap<String, Object>();
        // input check
        if (versionId == null) {
            map.put("error", "Version id is empty.");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        } else if (inputBody == null) {
            map.put("error", "Request body is empty.");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        try {
            String resultMessage = versionService.updateVersion(new Long(versionId), inputBody.getVersionName());
            if (!resultMessage.contains("error") && resultMessage != null) {
                map.put("message", resultMessage);
                return new ResponseEntity<>(map, HttpStatus.OK);
            } else
                return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            map.put("error", "error : No data found for the version id " + Long.toString(versionId));
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping(value="/version/{versionId}", produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> copyVersionInfo(@PathVariable Integer versionId, @RequestBody VersionCreateInputDTO inputBody) {
        log.info("> Copy and paste version info. versionId = " + Integer.toString(versionId) + ", inputBody = " + inputBody.toString());
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            Long newVersionId = versionService.insertCopiedVersion(new Long(versionId), inputBody);
            map.put("versionId", newVersionId);
            map.put("message", "Successfully created copied version");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (NotFoundException e) {
            map.put("error", "error : No data found for the version id " + Long.toString(versionId));
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value="/version/{versionId}", produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> deleteVersion(@PathVariable Integer versionId) {
        log.info("> Delete version. versionId = " + Integer.toString(versionId));
        Map<String, Object> map = new HashMap<String, Object>();
        if (versionService.deleteVersionInfo(new Long(versionId))) {
            map.put("message", "Successfully deleted version info.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            map.put("message", "Error occured during delete process.");
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // 버전에 연결된 UK가 하나도 없는 버전도 default로 적용될 가능성 있음!
    @PutMapping(value="/version/default/{versionId}", produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> applyDefaultVersion(@PathVariable Integer versionId) {
        log.info("> Apply default version. versionId = " + Integer.toString(versionId));
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            String message = versionService.applyDefaultVersion(new Long(versionId));
            map.put("message", message);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            map.put("error", "error : No data found for the version id " + Long.toString(versionId));
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }
}
