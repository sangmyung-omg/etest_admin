package com.tmax.eTest.Push.service;

import com.tmax.eTest.Push.dto.AdminPushRequestDTO;
import com.tmax.eTest.Push.dto.CategoryPushRequestDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@PropertySource("classpath:push-admin-config.properties")
@Service
@RequiredArgsConstructor
public class PushService {
    private Logger logger = LoggerFactory.getLogger(PushService.class);

    @Value("${backend.base.uri}")
    String baseUri;

    @Value("${push.api.admin.token}")
    String pushAdminTokenAPI;

    @Value("${push.api.admin.user}")
    String pushAdminUserAPI;

    @Value("${push.api.category}")
    String pushCategoryAPI;

    @Value("${push.api.category.user}")
    String pushCategoryUserAPI;

    public Mono<String> adminPushRequestByToken(AdminPushRequestDTO data) {
        return WebClient.create().post().uri(baseUri + pushAdminTokenAPI).bodyValue(data).retrieve().bodyToMono(String.class);
    }

    public Mono<String> adminPushRequestByUserUuid(AdminPushRequestDTO data) {
        return WebClient.create().post().uri(baseUri + pushAdminUserAPI).bodyValue(data).retrieve().bodyToMono(String.class);
    }

    public Mono<String> categoryPushRequest(CategoryPushRequestDTO data) {
        return WebClient.create().post().uri(baseUri + pushCategoryAPI).bodyValue(data).retrieve().bodyToMono(String.class);
    }

    public Mono<String> categoryPushRequestByUserUuid(CategoryPushRequestDTO data) {
        return WebClient.create().post().uri(baseUri + pushCategoryUserAPI).bodyValue(data).retrieve().bodyToMono(String.class);
    }
}
