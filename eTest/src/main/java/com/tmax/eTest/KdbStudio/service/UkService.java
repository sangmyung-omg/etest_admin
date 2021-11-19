package com.tmax.eTest.KdbStudio.service;

import java.util.List;

import com.tmax.eTest.Common.model.uk.UkDescriptionVersion;
import com.tmax.eTest.Common.repository.uk.UkDescriptionVersionRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UkService {
    
    @Autowired
    UkDescriptionVersionRepo ukVersionRepo;

    public List<UkDescriptionVersion> getAllUkInfoForVersion(Integer versionId) {
        List<UkDescriptionVersion> ukList = ukVersionRepo.findByVersionIdOrderByUkId(new Long(versionId));
        return ukList;
    }
}
