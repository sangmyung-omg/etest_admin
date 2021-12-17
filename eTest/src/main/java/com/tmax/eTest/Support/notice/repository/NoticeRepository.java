package com.tmax.eTest.Support.notice.repository;

import com.tmax.eTest.Common.model.support.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {}
