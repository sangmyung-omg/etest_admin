package com.tmax.eTest.Admin.faq.service;

import com.tmax.eTest.Admin.faq.dto.CreateFaqDto;
import com.tmax.eTest.Admin.faq.repository.FaqRepository;
import com.tmax.eTest.Admin.faq.repository.AdminFaqRepositorySupport;
import com.tmax.eTest.Admin.notice.dto.CreateNoticeRequestDto;
import com.tmax.eTest.Admin.util.ColumnNullPropertiesHandler;
import com.tmax.eTest.Auth.dto.CMRespDto;
import com.tmax.eTest.Common.model.support.FAQ;
import com.tmax.eTest.Common.model.support.Notice;
import com.tmax.eTest.Push.dto.CategoryPushRequestDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminFaqService {
    private static final Logger logger = LoggerFactory.getLogger(AdminFaqService.class);
    private final FaqRepository faqRepository;
    private final AdminFaqRepositorySupport adminFaqRepositorySupport;

    @Value("${file.path}")
    private String rootPath;

    @Transactional
    public CMRespDto<?> createFaq(CreateFaqDto createFaqDto) {
        String noticeImageFolderURL = rootPath + "faq/";
        Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());
        FAQ faq = null;
        if (createFaqDto.getImage() != null) {
            String imageName = UUID.randomUUID() + "_" + createFaqDto.getImage().getOriginalFilename();
            String imageUrlString = noticeImageFolderURL + imageName;
            Path imageUrlPath = Paths.get(imageUrlString);
            faq =
                    FAQ.builder()
                            .category(createFaqDto.getCategory())
                            .title(createFaqDto.getTitle())
                            .content(createFaqDto.getContent())
                            .draft(1)
                            .views((long) 0)
                            .dateAdd(currentDateTime)
                            .dateEdit(currentDateTime)
                            .imageUrl(imageUrlString)
                            .build();
            try {
                Files.write(imageUrlPath, createFaqDto.getImage().getBytes());
            } catch (Exception e) {
                throw new IllegalArgumentException("faq image save error");
            }
            faqRepository.save(faq);
        }
        if (createFaqDto.getImage() == null) {
            faq = FAQ.builder()
                    .category(createFaqDto.getCategory())
                    .title(createFaqDto.getTitle())
                    .content(createFaqDto.getContent())
                    .draft(1)
                    .views((long) 0)
                    .dateAdd(currentDateTime)
                    .dateEdit(currentDateTime)
                    .build();
            faqRepository.save(faq);
        }
        return new CMRespDto<>(200, "success", faq);
    }

    @Transactional
    public CMRespDto<?> draftFaq(CreateFaqDto createFaqDto) {
        String noticeImageFolderURL = rootPath + "faq/";
        Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());
        FAQ faq = null;
        if (createFaqDto.getImage() != null) {
            String imageName = UUID.randomUUID() + "_" + createFaqDto.getImage().getOriginalFilename();
            String imageUrlString = noticeImageFolderURL + imageName;
            Path imageUrlPath = Paths.get(imageUrlString);
            faq =
                    FAQ.builder()
                            .category(createFaqDto.getCategory())
                            .title(createFaqDto.getTitle())
                            .content(createFaqDto.getContent())
                            .draft(1)
                            .views((long) 0)
                            .dateAdd(currentDateTime)
                            .dateEdit(currentDateTime)
                            .imageUrl(imageUrlString)
                            .build();
            try {
                Files.write(imageUrlPath, createFaqDto.getImage().getBytes());
            } catch (Exception e) {
                throw new IllegalArgumentException("faq image save error");
            }
            faqRepository.save(faq);
        }
        if (createFaqDto.getImage() == null) {
            faq = FAQ.builder()
                    .category(createFaqDto.getCategory())
                    .title(createFaqDto.getTitle())
                    .content(createFaqDto.getContent())
                    .draft(1)
                    .views((long) 0)
                    .dateAdd(currentDateTime)
                    .dateEdit(currentDateTime)
                    .build();
            faqRepository.save(faq);
        }
        return new CMRespDto<>(200, "success", faq);
    }

    public FAQ getFaq(Long id) {
        if (faqRepository.findById(id).isPresent())
            return faqRepository.findById(id).get();
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
        return faqRepository.save(faq);
    }

    public void deleteFaq(Long id) {
        faqRepository.deleteById(id);
    }

    public void updateFaqViews(Long id) {
        FAQ faq = getFaq(id);
        if (faq == null) {
            throw new IllegalArgumentException("id have no faq");
        }
        faq.setViews(faq.getViews() + 1);
        faqRepository.save(faq);
    }
}
