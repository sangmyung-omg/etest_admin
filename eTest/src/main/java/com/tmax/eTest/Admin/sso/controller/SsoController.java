package com.tmax.eTest.Admin.sso.controller;

import com.tmax.eTest.Admin.managementHistory.filter.ManagementHistoryFilter;
import com.tmax.eTest.Auth.dto.AuthProvider;
import com.tmax.eTest.Auth.dto.CMRespDto;
import com.tmax.eTest.Auth.dto.PrincipalDetails;
import com.tmax.eTest.Auth.dto.Role;
import com.tmax.eTest.Auth.jwt.JwtTokenUtil;
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
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/sso/getToken")
    public CMRespDto<?> getToken(HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);
        Enumeration enumeration = httpSession.getAttributeNames();
        String tmp="";
        while (enumeration.hasMoreElements())
        {
            tmp=(String)enumeration.nextElement();
            logger.info(" key : value "+tmp+" : "+httpSession.getAttribute(tmp));
        }

        if (!(httpSession.getAttribute("user_id") == null)) {
            try {
                String user_id = (String) httpSession.getAttribute("user_id");
                logger.info("user_id : "+user_id);
                UserMaster userMaster = UserMaster.builder()
                        .providerId("user_id")
                        .email("user_id")
                        .name("user_id")
                        .provider(AuthProvider.valueOf("kakao"))
                        .role(Role.MASTER)
                        .build();
                userMaster.setName(user_id);
                PrincipalDetails principal = PrincipalDetails.create(userMaster);
                String token = jwtTokenUtil.generateAccessToken(principal);
                return new CMRespDto<>(200, "success", token);

            } catch (IllegalArgumentException e) {
                logger.debug("IllegalArgumentException e");
            }
        }
        return new CMRespDto<>(200, "fail", "fail");
    }


}
