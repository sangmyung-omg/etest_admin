package com.tmax.eTest.KdbStudio.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tmax.eTest.Common.model.uk.UkDescriptionVersion;
import com.tmax.eTest.Common.model.uk.VersionMaster;
import com.tmax.eTest.Common.repository.uk.UkDescriptionVersionRepo;
import com.tmax.eTest.Common.repository.uk.VersionMasterRepo;
import com.tmax.eTest.KdbStudio.dto.VersionCreateInputDTO;
import com.tmax.eTest.KdbStudio.dto.VersionGetOutputDTO;
import com.tmax.eTest.KdbStudio.util.UkVersionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VersionService {
    
    @Autowired
    VersionMasterRepo versionRepo;

    @Autowired
    UkDescriptionVersionRepo ukVersionRepo;
    
    @Autowired
    UkVersionManager versionManager;

    public List<VersionGetOutputDTO> getAllVersionInfo() {
        List<VersionGetOutputDTO> result = new ArrayList<VersionGetOutputDTO>();

        List<VersionMaster> queryResult = versionRepo.findAll();
        for (VersionMaster version : queryResult) {
            VersionGetOutputDTO dto = new VersionGetOutputDTO();
            dto.setVersionId(version.getVersionId());
            dto.setVersionName(version.getVersionName());
            dto.setVersionNum(version.getVersionNum());
            dto.setIsDefault(new Integer(version.getIsDefault()));
            dto.setIsDeleted(new Integer(version.getIsDeleted()));
            result.add(dto);
        }
        return result;
    }

    public Long insertVersion(VersionCreateInputDTO input) {
        // check if exists duplicated versionNum
        if (versionRepo.findByVersionNum(input.getVersionNum()).isPresent()) {
            return new Long(-1);
        }

        // get the latest version id
        Long newVersionId = versionManager.getLatestUkVersionId() + 1;

        // save the new version
        VersionMaster newVersion = new VersionMaster();

        newVersion.setVersionId(newVersionId);
        newVersion.setVersionName(input.getVersionName());
        newVersion.setVersionNum(input.getVersionNum());
        newVersion.setCreateDate(new Timestamp(System.currentTimeMillis()));
        newVersion.setEditDate(new Timestamp(System.currentTimeMillis()));
        newVersion.setIsDefault("0");
        newVersion.setIsDeleted("0");

        versionRepo.save(newVersion);
        log.info("Successfully saved new version info " + Long.toString(newVersionId));

        // Set latest version id into util module
        versionManager.setLatestUkVersionId(newVersionId);
        
        return newVersionId;
    }

    public String updateVersion(Long versionId, String versionName) throws NotFoundException {
        String message = "Successfully updated version information.";

        log.info("Getting existing version info by given id");
        VersionMaster existingVersion = versionRepo.findById(versionId).orElseThrow(() -> new NotFoundException("error : The version id given is not found, " + Long.toString(versionId)));

        existingVersion.setVersionName(versionName);
        versionRepo.save(existingVersion);
        log.info("Successfully updated existing version info " + Long.toString(versionId));

        return message;
    }

    public Long insertCopiedVersion(Long versionId, VersionCreateInputDTO input) throws NotFoundException {
        // get the latest version id and new id
        Long newVersionId = versionManager.getLatestUkVersionId() + 1;

        // get existing version object
        VersionMaster existingVersion = versionRepo.findById(versionId).orElseThrow(() -> new NotFoundException("error : The version id given is not found, " + Long.toString(versionId)));
        
        // save new version info into VERSION_MASTER
        VersionMaster newVersion = new VersionMaster();
        newVersion.setVersionId(newVersionId);
        newVersion.setVersionName(input.getVersionName());
        newVersion.setVersionNum(input.getVersionNum());
        newVersion.setCreateDate(existingVersion.getCreateDate());
        newVersion.setEditDate(existingVersion.getEditDate());
        newVersion.setIsDefault("0");
        newVersion.setIsDeleted("0");
        versionRepo.save(newVersion);

        // get uks of existing version and save with new version id (891 rows)
        List<UkDescriptionVersion> ukVersionList = ukVersionRepo.findByVersionIdOrderByUkId(versionId);
        List<UkDescriptionVersion> newVersionUkList = new ArrayList<UkDescriptionVersion>();
        for (UkDescriptionVersion ukInfo : ukVersionList) {
            UkDescriptionVersion newUkVersion = new UkDescriptionVersion();
            newUkVersion.setUkId(ukInfo.getUkId());
            newUkVersion.setUkName(ukInfo.getUkName());
            newUkVersion.setUkDescription(ukInfo.getUkDescription());
            newUkVersion.setExternalLink(ukInfo.getExternalLink());
            newUkVersion.setEditDate(ukInfo.getEditDate());
            newUkVersion.setVersionId(newVersionId);
            newVersionUkList.add(newUkVersion);
        }

        ukVersionRepo.saveAll(newVersionUkList);

        // Set latest version id into util module
        versionManager.setLatestUkVersionId(newVersionId);

        return newVersionId;
    }

    public boolean deleteVersionInfo(Long versionId) {
        // UK_DESCRIPTION_VERSION 에서 삭제x
        ukVersionRepo.deleteAllByVersionId(versionId);
        // VERSION_MASTER 에서 삭제
        versionRepo.deleteById(versionId);

        return true;
    }

    public String applyDefaultVersion(Long versionId) throws NotFoundException {
        // get current default version
        VersionMaster currentDefault = versionRepo.findByIsDefault("1");
        currentDefault.setIsDefault("0");

        // get target version
        VersionMaster newDefault = versionRepo.findById(versionId).orElseThrow(() -> new NotFoundException("error : The version id given is not found, " + Long.toString(versionId)));
        newDefault.setIsDefault("1");

        // save
        versionRepo.saveAll(Arrays.asList(currentDefault, newDefault));
        String message = "Successfully applied given version to default.";

        return message;
    }
}
