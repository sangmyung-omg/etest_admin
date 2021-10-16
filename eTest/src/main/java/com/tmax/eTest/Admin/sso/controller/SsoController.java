package com.tmax.eTest.Admin.sso.controller;

import com.tmax.eTest.Admin.managementHistory.filter.ManagementHistoryFilter;
import com.tmax.eTest.Admin.sso.service.SsoService;
import com.tmax.eTest.Auth.dto.AuthProvider;
import com.tmax.eTest.Auth.dto.CMRespDto;
import com.tmax.eTest.Auth.dto.PrincipalDetails;
import com.tmax.eTest.Auth.dto.Role;
import com.tmax.eTest.Auth.jwt.JwtTokenUtil;
import com.tmax.eTest.Auth.repository.UserRepository;
import com.tmax.eTest.Common.model.user.UserMaster;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.Enumeration;

@RestController
public class SsoController {
    private static final Logger logger = LoggerFactory.getLogger(ManagementHistoryFilter.class);


    @Autowired
    private SsoService ssoService;
    @GetMapping("/sso/getToken")
    public CMRespDto<?> getToken(HttpServletRequest request) {
        return ssoService.getToken(request);
    }
    @GetMapping("/sso/setToken")
    public CMRespDto<?> setToken(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("user_id","minjoon");
        logger.info("httpSession : "+ httpSession.getAttribute("user_id"));
        return new CMRespDto<>(200, "200", 200);
    }

}
