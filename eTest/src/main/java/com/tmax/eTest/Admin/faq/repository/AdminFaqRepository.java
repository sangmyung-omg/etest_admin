package com.tmax.eTest.Admin.faq.repository;

import com.tmax.eTest.Common.model.support.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminFaqRepository extends JpaRepository<FAQ, Long> {}
