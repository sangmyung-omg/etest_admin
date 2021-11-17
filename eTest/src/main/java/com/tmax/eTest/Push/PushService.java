package com.tmax.eTest.Push;

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

    @Value("${push.api.url}")
    String pushAPI;

    public Mono<String> adminPushRequest(AdminPushRequestDTO data) {
        return WebClient.create().post().uri(baseUri + pushAPI).bodyValue(data).retrieve().bodyToMono(String.class);
    }
}
