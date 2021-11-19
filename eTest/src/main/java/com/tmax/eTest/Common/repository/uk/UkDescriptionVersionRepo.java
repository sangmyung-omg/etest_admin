package com.tmax.eTest.Common.repository.uk;

import java.util.List;

import com.tmax.eTest.Common.model.uk.UkDescriptionVersion;
import com.tmax.eTest.Common.model.uk.UkDesriptionVersionCompositeKey;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UkDescriptionVersionRepo extends JpaRepository<UkDescriptionVersion, UkDesriptionVersionCompositeKey> {

    List<UkDescriptionVersion> findByVersionIdOrderByUkId(Long versionId);
}
