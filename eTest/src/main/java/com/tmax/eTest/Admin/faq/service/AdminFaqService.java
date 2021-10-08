package com.tmax.eTest.Admin.faq.service;

import com.tmax.eTest.Admin.faq.repository.AdminFaqRepository;
import com.tmax.eTest.Admin.faq.repository.AdminFaqRepositorySupport;
import com.tmax.eTest.Admin.util.ColumnNullPropertiesHandler;
import com.tmax.eTest.Common.model.support.FAQ;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminFaqService {
    private final AdminFaqRepository adminFaqRepository;
    private final AdminFaqRepositorySupport adminFaqRepositorySupport;

    public FAQ createFaq(FAQ faq) {
        Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());
        if (faq.getDraft() == 0)
            faq.setDateAdd(currentDateTime);
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
        ColumnNullPropertiesHandler.copyNonNullProperties(newFaq, faq);
        faq.setDateEdit(currentDateTime);
        return adminFaqRepository.save(faq);
    }

    public void deleteFaq(Long id) {
        adminFaqRepository.deleteById(id);
    }

    public void updateFaqViews(Long id) {
        FAQ faq = getFaq(id);
        faq.setViews(faq.getViews() + 1);
        adminFaqRepository.save(faq);
    }
}
