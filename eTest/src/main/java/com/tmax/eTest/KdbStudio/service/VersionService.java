package com.tmax.eTest.KdbStudio.service;

import java.util.List;

import com.tmax.eTest.Common.model.uk.VersionMaster;
import com.tmax.eTest.Common.repository.uk.VersionMasterRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VersionService {
    
    @Autowired
    VersionMasterRepo versionRepo;

    public List<VersionMaster> getAllVersionInfo() {
        List<VersionMaster> queryResult = versionRepo.findAll();
        return queryResult;
    }
}
