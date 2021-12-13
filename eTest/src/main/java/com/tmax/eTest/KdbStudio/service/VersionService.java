package com.tmax.eTest.KdbStudio.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

        // get the currently default version id
        Long defaultVersionId = versionManager.getCurrentUkVersionId();

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

        // get uks of existing version and save with new version id (891 rows)
        List<UkDescriptionVersion> ukVersionList = ukVersionRepo.findByVersionIdOrderByUkId(defaultVersionId);
        if (ukVersionList.size() == 0) {
            log.info("No UK list info for the default UK version id = " + Long.toString(defaultVersionId) + ". Please check the current default UK version id again.");
            return new Long(-10);
        }

        List<UkDescriptionVersion> newVersionUkList = new ArrayList<UkDescriptionVersion>();
        log.info("Copy and Save default UK list to newly created version.");
        for (UkDescriptionVersion ukInfo : ukVersionList) {
            UkDescriptionVersion newUkVersion = new UkDescriptionVersion();
            newUkVersion.setUkId(ukInfo.getUkId());
            newUkVersion.setUkName(ukInfo.getUkName());
            newUkVersion.setUkDescription(ukInfo.getUkDescription());
            newUkVersion.setExternalLink(ukInfo.getExternalLink());
            newUkVersion.setEditDate(ukInfo.getEditDate());
            newUkVersion.setVersionId(newVersionId);
            // ukVersionRepo.save(newUkVersion);
            newVersionUkList.add(newUkVersion);
            if (ukInfo.getUkId() % 200 == 0)
                log.info("Progress : " + Long.toString(ukInfo.getUkId()) + " / " + Integer.toString(ukVersionList.size()));
        }
        ukVersionRepo.saveAll(newVersionUkList);
        log.info("Total # of UKs being copied : " + Integer.toString(newVersionUkList.size()));

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
        // check if exists duplicated versionNum
        if (versionRepo.findByVersionNum(input.getVersionNum()).isPresent()) {
            return new Long(-1);
        }

        // get the latest version id and set new id
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

        log.info("Successfully created new version info " + Long.toString(newVersionId));

        // get uks of existing version and save with new version id (891 rows)
        List<UkDescriptionVersion> ukVersionList = ukVersionRepo.findByVersionIdOrderByUkId(versionId);
        if (ukVersionList.size() == 0) {
            log.info("No UK list info for the version id = " + Long.toString(versionId) + ". Please check the UK version id again.");
            return new Long(-10);
        }

        List<UkDescriptionVersion> newVersionUkList = new ArrayList<UkDescriptionVersion>();
        log.info("Copy and Save default UK list to newly created version.");
        for (UkDescriptionVersion ukInfo : ukVersionList) {
            UkDescriptionVersion newUkVersion = new UkDescriptionVersion();
            newUkVersion.setUkId(ukInfo.getUkId());
            newUkVersion.setUkName(ukInfo.getUkName());
            newUkVersion.setUkDescription(ukInfo.getUkDescription());
            newUkVersion.setExternalLink(ukInfo.getExternalLink());
            newUkVersion.setEditDate(ukInfo.getEditDate());
            newUkVersion.setVersionId(newVersionId);
            newVersionUkList.add(newUkVersion);

            if (ukInfo.getUkId() % 200 == 0)
                log.info("Progress : " + Long.toString(ukInfo.getUkId()) + " / " + Integer.toString(ukVersionList.size()));
        }
        ukVersionRepo.saveAll(newVersionUkList);
        log.info("Total # of UKs being copied : " + Integer.toString(newVersionUkList.size()));

        // Set latest version id into util module
        versionManager.setLatestUkVersionId(newVersionId);

        return newVersionId;
    }

    public boolean deleteVersionInfo(Long versionId) {
        // UK_DESCRIPTION_VERSION 에서 삭제x
        ukVersionRepo.deleteAllByVersionId(versionId);
        // VERSION_MASTER 에서 데이터 row 자체는 남겨두고, IS_DELETED 컬럼만 1로 변경 - 새 버전 생성 시 버전 num 알기 위해
        // versionRepo.deleteById(versionId);
        Optional<VersionMaster> queryResult = versionRepo.findById(versionId);
        if (queryResult.isPresent()) {
            VersionMaster versionInfo = queryResult.get();
            versionInfo.setIsDeleted("1");
            versionRepo.save(versionInfo);
            log.info("Successfully set is_deleted = 1 for version id = " + Long.toString(versionId));
            return true;
        } else {
            log.info("Version info not found for version id = " + Long.toString(versionId));
            return false;
        }
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

        versionManager.setCurrentUkVersionId(versionId);
        
        return message;
    }
}
