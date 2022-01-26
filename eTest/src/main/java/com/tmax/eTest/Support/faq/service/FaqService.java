package com.tmax.eTest.Support.faq.service;

import com.tmax.eTest.Support.faq.dto.CreateFaqDto;
import com.tmax.eTest.Support.faq.repository.FaqRepository;
import com.tmax.eTest.Support.faq.repository.FaqRepositorySupport;
import com.tmax.eTest.Admin.util.ColumnNullPropertiesHandler;
import com.tmax.eTest.Auth.dto.CMRespDto;
import com.tmax.eTest.Common.model.support.FAQ;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FaqService {
    private static final Logger logger = LoggerFactory.getLogger(FaqService.class);
    private final FaqRepository faqRepository;
    private final FaqRepositorySupport faqRepositorySupport;

    @Value("${file.path}")
    private String rootPath;

    public ResponseEntity<Resource> getFaqImage(String fileName) {
        String filePathString = rootPath + "/faq/";
        Path filePath = Paths.get(filePathString + fileName);
        HttpHeaders header = new HttpHeaders();
        Tika tika = new Tika();
        String mimeType;
        try {
            mimeType = tika.detect(filePath);
            header.add("Content-Type", mimeType);
        } catch (Exception e) {
            throw new IllegalArgumentException("mimeType error");
        }

        Resource resource = new FileSystemResource(filePath);
        return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
    }

    @Transactional
    public CMRespDto<?> createFaq(CreateFaqDto createFaqDto) throws IOException {

        Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());
        FAQ faq = null;
        if (createFaqDto.getImage() != null) {
            java.util.Base64.Encoder encoder = Base64.getEncoder();
            byte[] encodedBytes = encoder.encode(createFaqDto.getImage().getBytes());
            String imageEncoding = new String(encodedBytes);
            faq =
                    FAQ.builder()
                            .category(createFaqDto.getCategory())
                            .title(createFaqDto.getTitle())
                            .content(createFaqDto.getContent())
                            .draft(0)
                            .views((long) 0)
                            .dateAdd(currentDateTime)
                            .dateEdit(currentDateTime)
                            .imageEncoding(imageEncoding)
                            .build();
            faqRepository.save(faq);
        }
        if (createFaqDto.getImage() == null) {
            faq = FAQ.builder()
                    .category(createFaqDto.getCategory())
                    .title(createFaqDto.getTitle())
                    .content(createFaqDto.getContent())
                    .draft(0)
                    .views((long) 0)
                    .dateAdd(currentDateTime)
                    .dateEdit(currentDateTime)
                    .build();
            faqRepository.save(faq);
        }
        return new CMRespDto<>(200, "success", faq);
    }

    @Transactional
    public CMRespDto<?> draftFaq(CreateFaqDto createFaqDto) throws IOException {
        Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());
        FAQ faq = null;
        if (createFaqDto.getImage() != null) {
            java.util.Base64.Encoder encoder = Base64.getEncoder();
            byte[] encodedBytes = encoder.encode(createFaqDto.getImage().getBytes());
            String imageEncoding = new String(encodedBytes);
            faq =
                    FAQ.builder()
                            .category(createFaqDto.getCategory())
                            .title(createFaqDto.getTitle())
                            .content(createFaqDto.getContent())
                            .draft(1)
                            .views((long) 0)
                            .dateAdd(currentDateTime)
                            .dateEdit(currentDateTime)
                            .imageEncoding(imageEncoding)
                            .build();
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
        return faqRepositorySupport.faqList(categories, search);
    }

    public FAQ editFaq(Long id, CreateFaqDto createFaqDto) throws IOException {
        Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());
        FAQ faq = getFaq(id);
        if (faq == null) {
            logger.debug("faq is null");
            throw new IllegalArgumentException("faq is null");
        }
        faq.setDraft(createFaqDto.getDraft());
        faq.setCategory(createFaqDto.getCategory());
        faq.setTitle(createFaqDto.getTitle());
        faq.setContent(createFaqDto.getContent());
        if (createFaqDto.getImage() != null){
            java.util.Base64.Encoder encoder = Base64.getEncoder();
            byte[] encodedBytes = encoder.encode(createFaqDto.getImage().getBytes());
            String imageEncoding = new String(encodedBytes);
            faq.setImageEncoding(imageEncoding);
        }
        if (currentDateTime == null)
            throw new IllegalArgumentException("currentDateTime is null");
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
