package com.tmax.eTest.Admin.sso.service;

import com.tmax.eTest.Auth.dto.AuthProvider;
import com.tmax.eTest.Auth.dto.CMRespDto;
import com.tmax.eTest.Auth.dto.PrincipalDetails;
import com.tmax.eTest.Auth.dto.Role;
import com.tmax.eTest.Auth.jwt.JwtTokenUtil;
import com.tmax.eTest.Auth.repository.UserRepository;
import com.tmax.eTest.Common.model.user.UserMaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.Optional;
import java.util.UUID;

@Service
public class SsoService {
    private static final Logger logger = LoggerFactory.getLogger(SsoService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Transactional
    public CMRespDto<?> getToken(HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);

        // AttributeName 출력
        Enumeration enumeration = httpSession.getAttributeNames();
        String tmp = "";
        while (enumeration.hasMoreElements()) {
            tmp = (String) enumeration.nextElement();
            logger.info(" key : value -> " + tmp + " : " + httpSession.getAttribute(tmp));
        }
        /***
         * 기존에 sso로그인 한 유저라면 바로 로그인
         * 처음 sso로그인 했다면 강제회원가입 후 로그인
         */
        if (!(httpSession.getAttribute("user_id") == null)) {
            try {
                String user_id = (String) httpSession.getAttribute("user_id");
                Optional<UserMaster> userMasterOptional = userRepository.findByUserUuid(user_id);
                if (!(userMasterOptional.isPresent())) {
                    UserMaster userMaster = UserMaster.builder()
                            .nickname(user_id)
                            .email(user_id)
                            .provider(AuthProvider.kakao)
                            .role(Role.MASTER)
                            .userUuid(user_id)
                            .providerId(user_id)
                            .older_than_14(true)
                            .service_agreement(true)
                            .collect_info(true)
                            .build();
                    userRepository.save(userMaster);
                    PrincipalDetails principal = PrincipalDetails.create(userMaster);
                    String jwtToken = jwtTokenUtil.generateAccessToken(principal);
                    return new CMRespDto<>(201, "first login", jwtToken);
                } else {
                    PrincipalDetails principal = PrincipalDetails.create(userMasterOptional.get());
                    String jwtToken = jwtTokenUtil.generateAccessToken(principal);
                    return new CMRespDto<>(200, "exist user", jwtToken);
                }
            } catch (IllegalArgumentException e) {
                logger.info("IllegalArgumentException");
            }
        }
        return new CMRespDto<>(500,"500","session error");
    }
}
