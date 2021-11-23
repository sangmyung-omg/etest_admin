package com.tmax.eTest.KdbStudio.service;

import java.util.List;

import com.tmax.eTest.Common.model.uk.UkDescriptionVersion;
import com.tmax.eTest.Common.model.uk.UkDesriptionVersionCompositeKey;
import com.tmax.eTest.Common.repository.uk.UkDescriptionVersionRepo;
import com.tmax.eTest.KdbStudio.dto.UkUpdateDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;

@Service
public class UkService {
    
    @Autowired
    UkDescriptionVersionRepo ukVersionRepo;

    public List<UkDescriptionVersion> getAllUkInfoForVersion(Integer versionId) {
        List<UkDescriptionVersion> ukList = ukVersionRepo.findByVersionIdOrderByUkId(new Long(versionId));
        return ukList;
    }

    public String updateUkInfo(Long ukId, Long versionId, UkUpdateDTO input) throws NotFoundException {
        // get existing uk info
        UkDesriptionVersionCompositeKey keyset = new UkDesriptionVersionCompositeKey();
        keyset.setUkId(ukId);
        keyset.setVersionId(versionId);
        UkDescriptionVersion ukInfo = ukVersionRepo.findById(keyset).orElseThrow(() -> new NotFoundException("error : The version id given is not found, " + Long.toString(versionId)));

        // update uk info
        ukInfo.setUkName(input.getUkName());
        if (input.getUkDescription() != null && !input.getUkDescription().equalsIgnoreCase(""))
            ukInfo.setUkDescription(input.getUkDescription());
        ukVersionRepo.save(ukInfo);

        return "Successfully updated Uk info.";
    }
}
