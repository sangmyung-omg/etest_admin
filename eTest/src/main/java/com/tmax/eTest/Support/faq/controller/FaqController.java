package com.tmax.eTest.Support.faq.controller;

import com.tmax.eTest.Support.faq.dto.CreateFaqDto;
import com.tmax.eTest.Support.faq.service.FaqService;
import com.tmax.eTest.Auth.dto.CMRespDto;
import com.tmax.eTest.Common.model.support.FAQ;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("master")
public class FaqController {
    private final FaqService faqService;

    @Value("${file.path}")
    private String rootPath;

    @GetMapping(value = "/faq/image")
    public ResponseEntity<Resource> getFaqImage(@Param("fileName")String fileName ) {
        HttpHeaders header = new HttpHeaders();
        Path imageUrl = Paths.get(rootPath + "/faq/" + fileName);

        Tika tika = new Tika();
        String mimeType;
        try {
            mimeType = tika.detect(imageUrl);
            header.add("Content-Type", mimeType);
        } catch (IOException e) {
            throw new IllegalArgumentException("mime type error");
        }
        return faqService.getFaqImage(fileName);
    }

    /**
     * 자주 묻는 질문 생성
     */
    @PostMapping("faq")
    public CMRespDto<?> createFaq(@ModelAttribute CreateFaqDto createFaqDto) throws IOException {
        return faqService.createFaq(createFaqDto);
    }

    /**
     * 자주 묻는 질문 조회
     * @param id    조회할 자주 묻는 질문 id
     */
    @GetMapping("faq")
    public ResponseEntity<FAQ> getFaq(@RequestParam Long id) {
        return ResponseEntity.ok(faqService.getFaq(id));
    }

    /**
     * 자주 묻는 질문 전체 조회
     */
    @GetMapping("faq/all")
    public ResponseEntity<List<FAQ>> getAllFaq
    (@RequestParam(required = false) List<String> categories, @RequestParam(required = false) String search) {
        return ResponseEntity.ok(faqService.getAllFaq(categories, search));
    }

    /**
     * 자주 묻는 질문 수정
     * @param id        수정할 자주 묻는 질문 id
     */
    @PutMapping("faq")
    public ResponseEntity<FAQ> editFaq(@RequestParam Long id, @ModelAttribute CreateFaqDto createFaqDto) throws IOException {
        return ResponseEntity.ok(faqService.editFaq(id, createFaqDto));
    }

    /**
     * 자주 묻는 질문 삭제
     * @param id    삭제할 자주 묻는 질문 id
     */
    @DeleteMapping("faq")
    public String deleteFaq(@RequestParam Long id) {
        faqService.deleteFaq(id);
        return "faq deleted";
    }

    /**
     * 자주 묻는 질문 임시저장
     */
    @PostMapping("faq/draft")
    public CMRespDto<?> draftFaq(@ModelAttribute CreateFaqDto createFaqDto) throws IOException {
        return faqService.draftFaq(createFaqDto);
    }
}
