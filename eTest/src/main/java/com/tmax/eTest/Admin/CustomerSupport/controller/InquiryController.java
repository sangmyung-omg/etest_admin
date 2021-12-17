package com.tmax.eTest.Admin.CustomerSupport.controller;


import com.tmax.eTest.Auth.dto.PrincipalDetails;
import com.tmax.eTest.Admin.CustomerSupport.model.dto.InquiryAnswerDTO;
import com.tmax.eTest.Admin.CustomerSupport.model.dto.InquiryDTO;
import com.tmax.eTest.Admin.CustomerSupport.service.InquiryService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController("CustomerSupportInquiryController")
@RequestMapping(value="submaster/customerSupport", produces = MediaType.APPLICATION_JSON_VALUE)
public class InquiryController {
    protected final Log LOGGER = LogFactory.getLog(getClass());

    @Value("${file.path}")
    private String path;

    @Autowired
    InquiryService inquiryService;

    @RequestMapping(value="/inquiries", method = RequestMethod.GET)
    public ResponseEntity<?> getInquiryList() {

        List<InquiryDTO> inquiryList = inquiryService.getInquiryList();
        return ResponseEntity.ok().body(inquiryList);
    }

    @RequestMapping(value="/inquiry", method = RequestMethod.GET)
    public ResponseEntity<?> getInquiryDetail(@RequestParam(value="inquiryId") Long id, @AuthenticationPrincipal PrincipalDetails principalDetails){

        InquiryDTO inquiry = inquiryService.getInquiryDetails(id);
        return ResponseEntity.ok().body(inquiry);
    }

    @RequestMapping(value="/inquiry", method = RequestMethod.PUT)
    public ResponseEntity<?> answerInquiry(@RequestParam(value="inquiryId") Long id,
                                           @RequestBody InquiryAnswerDTO inquiryAnswerDTO,
                                           @AuthenticationPrincipal PrincipalDetails principalDetails) {

        String admin_uuid = principalDetails.getUserUuid();
        String admin_nickname = principalDetails.getNickname();
        InquiryDTO inquiry = inquiryService.answerInquiry(id,admin_uuid, admin_nickname, inquiryAnswerDTO.getAnswer());
        return ResponseEntity.ok().body(inquiry);
    }

    @RequestMapping(value="/inquiry", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteInquiry(@RequestParam(value="inquiryId") Long id) {
        inquiryService.deleteInquiry(id);
        return ResponseEntity.ok().body("success delete inquiry");
    }

    @GetMapping(value="/inquiry/attachment")
    public ResponseEntity<Resource> attachment(@Param("filename") String filename){
        String temp = path+"/inquiry/";
        Path filePath = null;
        filePath = Paths.get(temp+filename);
        HttpHeaders header = new HttpHeaders();
        Tika tika = new Tika();
        String mimeType;
        try {
            mimeType = tika.detect(filePath);
            header.add("Content-Type", mimeType);
        } catch (IOException e) {
            LOGGER.debug("mime type Error");
        }

        Resource resource = new FileSystemResource(filePath);
        return new ResponseEntity<Resource>(resource,header, HttpStatus.OK);
    }
}
