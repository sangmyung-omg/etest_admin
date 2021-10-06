package com.tmax.eTest.Admin.permissionManagement.service;

import com.tmax.eTest.Admin.permissionManagement.dto.PermissionManagementDTO;
import com.tmax.eTest.Admin.permissionManagement.dto.PermissionUpdateDTO;
import com.tmax.eTest.Admin.permissionManagement.dto.PermissionUserMasterSearchDTO;
import com.tmax.eTest.Admin.permissionManagement.repository.PermissionManagementRepository;
import com.tmax.eTest.Auth.dto.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PermissionManagementService {
    private final PermissionManagementRepository permissionManagementRepository;

    public List<PermissionManagementDTO> masterList(Role role, String search){
        return permissionManagementRepository.masterList(role, search);
    }

    public List<PermissionUserMasterSearchDTO> userSearch(String search) {
        return permissionManagementRepository.userSearch(search);
    }

    public void updatePermission(PermissionUpdateDTO permissionUpdateDTO){
        List<String> userUuidList = permissionUpdateDTO.getUserUuidList();
        Role role = permissionUpdateDTO.getRole();
        String ip = permissionUpdateDTO.getIp();
        for (String userUuid : userUuidList)
            permissionManagementRepository.updatePermission(userUuid, role, ip);
    }
}