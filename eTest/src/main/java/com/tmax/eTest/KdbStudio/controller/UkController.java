package com.tmax.eTest.KdbStudio.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmax.eTest.Common.repository.uk.UkDescriptionVersionRepo;
import com.tmax.eTest.KdbStudio.dto.UkGetOutputDTO;
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

import org.springframework.web.bind.annotation.RequestBody;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UkController {
    
    @Autowired
    UkService ukService;

    @Autowired
    UkDescriptionVersionRepo ukRepo;

    @GetMapping(value = "/uk", produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> getUkInfo(@RequestParam Integer versionId) {
        log.info("> Getting UKs for version : " + versionId);

        List<UkGetOutputDTO> ukList = ukService.getUkInfo(versionId);
        return new ResponseEntity<>(ukList, HttpStatus.OK);
    }

    @GetMapping(value = "/uk/page", produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> getPagedUkInfo(@RequestParam Integer versionId,
                                                @RequestParam(required = false) List<String> part,
                                                @RequestParam Integer page,
                                                @RequestParam Integer size) {

        log.info("> Getting UKs for version : " + versionId + ". part = " + part.toString() + ", page = " + Integer.toString(page) + ", size = " + Integer.toString(size));
        Map<String, Object> result = ukService.getAllPagedUkInfoForVersion(versionId, page, size);
        
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/uk", produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> updateUkInfo(@RequestParam Integer ukId, @RequestParam Integer versionId,
                                                    @RequestBody UkUpdateDTO inputBody) {
        log.info("> Update Uk Info. ukId : " + Integer.toString(ukId) + ", versionId : " + Integer.toString(versionId) + ", inputBody : " + inputBody);
        Map<String, Object> map = new HashMap<String, Object>();
    
        String description = inputBody.getUkDescription();
        String ukName = inputBody.getUkName();
        
        // input check
        if (description != null) {
            if (description.equalsIgnoreCase(""))
                description = null;
        }
        if (ukName != null) {
            if (ukName.equalsIgnoreCase(""))
                ukName = null;
        }
        if (description == null && ukName == null) {
            map.put("error", "ukName and ukDescription are empty.");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        try {
            String message = ukService.updateUkInfo(new Long(ukId), new Long(versionId), ukName, description);
            map.put("message", message);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (NotFoundException e) {
            log.info("error : No data found for the version id = " + Long.toString(versionId) + " and uk id = " + Long.toString(ukId));
            map.put("error", "error : No data found for the version id = " + Long.toString(versionId) + " and uk id = " + Long.toString(ukId));
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }
}
