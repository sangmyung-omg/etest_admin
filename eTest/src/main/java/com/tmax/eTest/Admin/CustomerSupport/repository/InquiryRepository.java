package com.tmax.eTest.Admin.CustomerSupport.repository;

import com.tmax.eTest.Common.model.support.Inquiry;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface InquiryRepository extends PagingAndSortingRepository<Inquiry,Long> {
    List<Inquiry> findAll(Sort sort);
    List<Inquiry> findAll();
    Optional<Inquiry> findById(Long id);
}
