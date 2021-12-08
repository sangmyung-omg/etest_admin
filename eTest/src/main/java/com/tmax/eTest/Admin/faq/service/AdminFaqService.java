package com.tmax.eTest.Admin.faq.service;

import com.tmax.eTest.Admin.faq.repository.AdminFaqRepository;
import com.tmax.eTest.Admin.faq.repository.AdminFaqRepositorySupport;
import com.tmax.eTest.Admin.util.ColumnNullPropertiesHandler;
import com.tmax.eTest.Common.model.support.FAQ;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminFaqService {
    private static final Logger logger = LoggerFactory.getLogger(AdminFaqService.class);
    private final AdminFaqRepository adminFaqRepository;
    private final AdminFaqRepositorySupport adminFaqRepositorySupport;

    public FAQ createFaq(FAQ faq) {
        Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());
        faq.setDraft(0);
        faq.setDateAdd(currentDateTime);
        return adminFaqRepository.save(faq);
    }

    public FAQ draftFaq(FAQ faq) {
        faq.setDraft(1);
        return adminFaqRepository.save(faq);
    }

    public FAQ getFaq(Long id) {
        if (adminFaqRepository.findById(id).isPresent())
            return adminFaqRepository.findById(id).get();
        return null;
    }

    public List<FAQ> getAllFaq(List<String> categories, String search) {
        return adminFaqRepositorySupport.faqList(categories, search);
    }

    public FAQ editFaq(Long id, FAQ newFaq) {
        Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());
        FAQ faq = getFaq(id);
        if (faq == null) {
            logger.debug("faq is null");
            throw new IllegalArgumentException("faq is null");
        }
        ColumnNullPropertiesHandler.copyNonNullProperties(newFaq, faq);
        if (currentDateTime == null) {
            throw new IllegalArgumentException("currentDateTime is null");
        }
        faq.setDateEdit(currentDateTime);
        return adminFaqRepository.save(faq);
    }

    public void deleteFaq(Long id) {
        adminFaqRepository.deleteById(id);
    }

    public void updateFaqViews(Long id) {
        FAQ faq = getFaq(id);
        if (faq == null) {
            throw new IllegalArgumentException("id have no faq");
        }
        faq.setViews(faq.getViews() + 1);
        adminFaqRepository.save(faq);
    }
}
