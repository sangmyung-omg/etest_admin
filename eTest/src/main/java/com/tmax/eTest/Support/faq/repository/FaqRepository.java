package com.tmax.eTest.Support.faq.repository;

import com.tmax.eTest.Common.model.support.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<FAQ, Long> {}
