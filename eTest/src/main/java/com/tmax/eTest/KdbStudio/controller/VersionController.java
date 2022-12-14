package com.tmax.eTest.KdbStudio.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmax.eTest.Common.model.uk.VersionMaster;
import com.tmax.eTest.KdbStudio.dto.VersionCreateInputDTO;
import com.tmax.eTest.KdbStudio.dto.VersionGetOutputDTO;
import com.tmax.eTest.KdbStudio.dto.VersionUpdateInputDTO;
import com.tmax.eTest.KdbStudio.service.VersionService;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
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
        log.info("> /version GET. Getting all version infos");
        Map<String, Object> map = new HashMap<String, Object>();

        List<VersionGetOutputDTO> versionList = versionService.getAllVersionInfo();

        if (versionList == null) 
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        // ordering
        CompareVersionMinorNum comp1 = new CompareVersionMinorNum();
        CompareVersionMajorNum comp2 = new CompareVersionMajorNum();
        Collections.sort(versionList, comp1);
        Collections.sort(versionList, comp2);

        // log.info(versionList.get(versionList.size()-1).getVersionNum());

        map.put("message", "Successfully returned version informations.");
        map.put("versionList", versionList);
        
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping(value="/version", produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> createNewVersion(@RequestBody VersionCreateInputDTO inputBody) {
        log.info("> /version POST. Creating new version. input = " + inputBody.toString());
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
        } else if (newVersionId == -10) {
            map.put("message", "No UK list info for the default UK version. Please check the default UK version again.");
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            map.put("message", "Successfully created new version.");
            map.put("versionId", newVersionId);
    
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @PutMapping(value="/version/{versionId}", produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> updateVersionInfo(@PathVariable Integer versionId, @RequestBody VersionUpdateInputDTO inputBody) {
        log.info("> /version/{versionId} PUT. Update version info. versionId = " + Integer.toString(versionId) + ", versionName = " + inputBody);
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
        log.info("> /version/{versionId} POST. Copy and paste version info. versionId = " + Integer.toString(versionId) + ", inputBody = " + inputBody.toString());
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            Long newVersionId = versionService.insertCopiedVersion(new Long(versionId), inputBody);
            if (newVersionId == -1) {
                map.put("message", "Version Num '" + inputBody.getVersionNum() + "' already exists. Please use new version number.");
                return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
            } else if (newVersionId == -10) {
                map.put("message", "No UK list info for the version id = " + Integer.toString(versionId) + ". Please check the UK version id again.");
                return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                map.put("versionId", newVersionId);
                map.put("message", "Successfully created copied version");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        } catch (NotFoundException e) {
            map.put("error", "error : No data found for the version id " + Long.toString(versionId));
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value="/version/{versionId}", produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> deleteVersion(@PathVariable Integer versionId) {
        log.info("> /version/{versionId} DELETE. Delete version. versionId = " + Integer.toString(versionId));
        Map<String, Object> map = new HashMap<String, Object>();
        if (versionService.deleteVersionInfo(new Long(versionId))) {
            map.put("message", "Successfully deleted version info.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            map.put("message", "Error occured during delete process.");
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // ????????? ????????? UK??? ????????? ?????? ????????? default??? ????????? ????????? ??????!
    @PutMapping(value="/version/default/{versionId}", produces = "application/json; charset=utf-8")
    public ResponseEntity<Object> applyDefaultVersion(@PathVariable Integer versionId) {
        log.info("> /version/default/{versionId} PUT. Apply default version. versionId = " + Integer.toString(versionId));
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            String message = versionService.applyDefaultVersion(new Long(versionId));
            map.put("message", message);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (NotFoundException e) {
            map.put("error", "error : No data found for the version id " + Long.toString(versionId));
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }

    private class CompareVersionMinorNum implements Comparator<VersionGetOutputDTO> {
        @Override
        public int compare(VersionGetOutputDTO first, VersionGetOutputDTO second) {
            String first_num = first.getVersionNum().trim();
            String sec_num = second.getVersionNum().trim();
            first_num = first_num.split(" ")[first_num.split(" ").length-1];
            sec_num = sec_num.split(" ")[sec_num.split(" ").length-1];
            first_num = first_num.split("\\.")[first_num.split("\\.").length-1];
            sec_num = sec_num.split("\\.")[sec_num.split("\\.").length-1];

            try {
                float first_float = Float.parseFloat(first_num);
                float sec_float = Float.parseFloat(sec_num);
                
                if (first_float > sec_float) {
                    return 1;
                } else if (first_float < sec_float) {
                    return -1;
                } else return 0;
            } catch (TypeMismatchException e) {
                return 500;
            } catch (ParseException e) {
                return 501;
            }
        }
        
    }

    private class CompareVersionMajorNum implements Comparator<VersionGetOutputDTO> {
        @Override
        public int compare(VersionGetOutputDTO first, VersionGetOutputDTO second) {
            String first_num = first.getVersionNum().trim();
            String sec_num = second.getVersionNum().trim();
            first_num = first_num.split(" ")[first_num.split(" ").length-1];
            sec_num = sec_num.split(" ")[sec_num.split(" ").length-1];
            first_num = first_num.split("\\.")[0];
            sec_num = sec_num.split("\\.")[0];

            try {
                float first_float = Float.parseFloat(first_num);
                float sec_float = Float.parseFloat(sec_num);
                
                if (first_float > sec_float) {
                    return 1;
                } else if (first_float < sec_float) {
                    return -1;
                } else return 0;
            } catch (TypeMismatchException e) {
                return 500;
            } catch (ParseException e) {
                return 501;
            }
        }
        
    }
}
