package com.tmax.eTest.Push.controller;

import com.tmax.eTest.Push.dto.AdminPushRequestDTO;
import com.tmax.eTest.Push.dto.CategoryPushRequestDTO;
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

    @PostMapping("admin")
    public Mono<String> adminNotification(@RequestBody AdminPushRequestDTO adminPushRequestDTO) {
        return pushService.adminPushRequest(adminPushRequestDTO);
    }

    @PostMapping("category")
    public Mono<String> categoryNotification(@RequestBody CategoryPushRequestDTO categoryPushRequestDTO) {
        return pushService.categoryPushRequest(categoryPushRequestDTO);
    }
}
