package com.tmax.eTest.Push.controller;

import com.tmax.eTest.Push.dto.PushRequestDTO;
import com.tmax.eTest.Push.service.PushService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("push")
@RequiredArgsConstructor
public class PushController {
    private final PushService pushService;

    @PostMapping("admin/token")
    public Mono<String> adminPushRequestByToken(@RequestBody PushRequestDTO pushRequestDTO) {
        return pushService.adminPushRequestByToken(pushRequestDTO);
    }

    @PostMapping("admin/user")
    public Mono<String> adminPushRequestByUserUuid(@RequestBody PushRequestDTO pushRequestDTO) {
        return pushService.adminPushRequestByUserUuid(pushRequestDTO);
    }

    @PostMapping("category")
    public Mono<String> categoryPushRequest(@RequestBody PushRequestDTO pushRequestDTO) {
        return pushService.categoryPushRequest(pushRequestDTO);
    }

    @PostMapping("category/user")
    public Mono<String> categoryPushRequestByUserUuid(@RequestBody PushRequestDTO pushRequestDTO) {
        return pushService.categoryPushRequestByUserUuid(pushRequestDTO);
    }
}
