package com.tmax.eTest.Common.repository.uk;

import java.util.List;

import com.tmax.eTest.Common.model.uk.UkDescriptionVersion;
import com.tmax.eTest.Common.model.uk.UkDesriptionVersionCompositeKey;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UkDescriptionVersionRepo extends JpaRepository<UkDescriptionVersion, UkDesriptionVersionCompositeKey> {

    List<UkDescriptionVersion> findByVersionIdOrderByUkId(Long versionId);

    @EntityGraph(attributePaths = {"ukMaster", "ukMaster.videoUks", "ukMaster.videoUks.video"})
    @Query("SELECT udv FROM UkDescriptionVersion udv WHERE udv.versionId = ?1 ORDER BY udv.ukId")
    List<UkDescriptionVersion> findByVersionIdFetchJoin(Long versionId);

    Page<UkDescriptionVersion> findByVersionId(Long versionId, Pageable pageable);

    @Transactional
    void deleteAllByVersionId(Long versionId);
}
