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

    @PostMapping("admin/token")
    public Mono<String> adminPushRequestByToken(@RequestBody AdminPushRequestDTO adminPushRequestDTO) {
        return pushService.adminPushRequestByToken(adminPushRequestDTO);
    }

    @PostMapping("admin/user")
    public Mono<String> adminPushRequestByUserUuid(@RequestBody AdminPushRequestDTO adminPushRequestDTO) {
        return pushService.adminPushRequestByUserUuid(adminPushRequestDTO);
    }

    @PostMapping("category")
    public Mono<String> categoryPushRequest(@RequestBody CategoryPushRequestDTO categoryPushRequestDTO) {
        return pushService.categoryPushRequest(categoryPushRequestDTO);
    }

    @PostMapping("category/user")
    public Mono<String> categoryPushRequestByUserUuid(@RequestBody CategoryPushRequestDTO categoryPushRequestDTO) {
        return pushService.categoryPushRequestByUserUuid(categoryPushRequestDTO);
    }
}
