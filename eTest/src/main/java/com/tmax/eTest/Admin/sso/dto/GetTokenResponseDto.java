package com.tmax.eTest.Admin.sso.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetTokenResponseDto {
    private String jwtToken;
    private String user_id;
}
