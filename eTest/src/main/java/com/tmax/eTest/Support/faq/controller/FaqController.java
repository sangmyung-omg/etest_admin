package com.tmax.eTest.Support.faq.controller;

import com.tmax.eTest.Support.faq.dto.CreateFaqDto;
import com.tmax.eTest.Support.faq.service.FaqService;
import com.tmax.eTest.Auth.dto.CMRespDto;
import com.tmax.eTest.Common.model.support.FAQ;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("master")
public class FaqController {
    private final FaqService faqService;

    /**
     * 자주 묻는 질문 생성
     */
    @PostMapping("faq")
    public CMRespDto<?> createFaq(@ModelAttribute CreateFaqDto createFaqDto) {
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
     * @param faq    자주 묻는 질문 정보
     */
    @PutMapping("faq")
    public ResponseEntity<FAQ> editFaq(@RequestParam Long id, @RequestBody FAQ faq) {
        return ResponseEntity.ok(faqService.editFaq(id, faq));
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
    public CMRespDto<?> draftFaq(@ModelAttribute CreateFaqDto createFaqDto) {
        return faqService.draftFaq(createFaqDto);
    }
}
