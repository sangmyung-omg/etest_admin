package com.tmax.eTest.Admin.ManageUser.controller;

import com.tmax.eTest.Admin.ManageUser.model.dto.UserInfoDTO;
import com.tmax.eTest.Admin.ManageUser.model.dto.UserPopupDTO;
import com.tmax.eTest.Admin.ManageUser.service.ManageUserService;
import com.tmax.eTest.Common.model.report.DiagnosisReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "master/manageUser",produces = MediaType.APPLICATION_JSON_VALUE)
public class ManageUserController {

    @Autowired
    ManageUserService manageUserService;

    // TODO
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<?> listUsers() {

        List<UserInfoDTO> listUserInfo = manageUserService.listUserInfo();
        return ResponseEntity.ok().body(listUserInfo);
    }

    // TODO
    @RequestMapping(value = "/user/resasonForDownload", method = RequestMethod.POST)
    public ResponseEntity<?> saveUserReasonForDownload() {
        return ResponseEntity.ok().body("reasonForDownload");
    }

    @RequestMapping(value = "/user/profilePopup", method = RequestMethod.GET)
    public ResponseEntity<?> getUserProfilePopupData(@RequestParam(value="user_uuid") String user_uuid) {
        UserPopupDTO userPopupDTO = manageUserService.getUserPopupData(user_uuid);
        return ResponseEntity.ok().body(userPopupDTO);
    }

    @RequestMapping(value = "/user/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@RequestParam(value="user_uuid") String user_uuid) {

        manageUserService.deleteUserById(user_uuid);
        return ResponseEntity.ok().body("success delete user");

    }

    @RequestMapping(value = "/user/reportPopup", method = RequestMethod.GET)
    public ResponseEntity<?> getUserReportPopupData(@RequestParam(value="user_uuid") String user_uuid) {

        List<DiagnosisReport> diagnosisReports = manageUserService.getUserDiagnosisReports(user_uuid);
        return ResponseEntity.ok().body(diagnosisReports);
    }

    // TODO
    @RequestMapping(value = "/user/bookmarkPopup", method = RequestMethod.GET)
    public ResponseEntity<?> getUserBookmarkPopupData(@RequestParam(value="user_uuid") String user_uuid){

        return ResponseEntity.accepted().body("bookmarkPopup");
    }

}
