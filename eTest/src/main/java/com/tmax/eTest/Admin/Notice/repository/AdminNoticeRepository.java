package com.tmax.eTest.Admin.Notice.repository;

import com.tmax.eTest.Common.model.support.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminNoticeRepository extends JpaRepository<Notice, Long> {}
