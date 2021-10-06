package com.tmax.eTest.Admin.permissionManagement.controller;

import com.tmax.eTest.Admin.permissionManagement.dto.PermissionManagementDTO;
import com.tmax.eTest.Admin.permissionManagement.dto.PermissionUpdateDTO;
import com.tmax.eTest.Admin.permissionManagement.dto.PermissionUserMasterSearchDTO;
import com.tmax.eTest.Admin.permissionManagement.service.PermissionManagementService;
import com.tmax.eTest.Auth.dto.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("master/permission_management")
public class PermissionManagementController {
    private final PermissionManagementService permissionManagementService;

    /**
     * 관리자 조회
     * @param role      권한 필터 (MASTER, SUB_MASTER)
     * @param search    검색할 이름 또는 이메일
     */
    @GetMapping()
    public ResponseEntity<List<PermissionManagementDTO>> masterList
            (@RequestParam(required = false) String role, @RequestParam(required = false) String search) {
        if (role == null)
            return ResponseEntity.ok(permissionManagementService.masterList(null, search));
        return ResponseEntity.ok(permissionManagementService.masterList(Role.valueOf(role), search));
    }

    /**
     * 관리자 생성에서 이름 검색
     * @param search    검색할 이름
     */
    @GetMapping("user_search")
    public ResponseEntity<List<PermissionUserMasterSearchDTO>> userSearch (@RequestParam(required = false) String search) {
        return ResponseEntity.ok(permissionManagementService.userSearch(search));
    }

    /**
     * 관리자 권한 여러개 삭제 / 관리자 권한 변경
     * @param permissionUpdateDTO   권한을 변경할 관리자 uuid 리스트, 변경할 권한, 변경할 IP
     */
    @PostMapping()
    public String updatePermission(@RequestBody PermissionUpdateDTO permissionUpdateDTO){
        permissionManagementService.updatePermission(permissionUpdateDTO);
        return "Permission update successful";
    }
}
