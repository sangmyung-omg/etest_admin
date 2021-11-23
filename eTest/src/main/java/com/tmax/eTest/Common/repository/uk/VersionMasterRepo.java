package com.tmax.eTest.Common.repository.uk;

import java.util.List;
import java.util.Optional;

import com.tmax.eTest.Common.model.uk.VersionMaster;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionMasterRepo extends JpaRepository<VersionMaster, Long> {
    VersionMaster findByIsDefault(String isDefault);

    List<VersionMaster> findByOrderByVersionIdDesc();

    Optional<VersionMaster> findByVersionNum(String versionNum);
}
