package com.tmax.eTest.Admin.notice.controller;

import com.tmax.eTest.Admin.notice.dto.CreateNoticeRequestDto;
import com.tmax.eTest.Admin.notice.service.NoticeService;
import com.tmax.eTest.Auth.dto.CMRespDto;
import com.tmax.eTest.Common.model.support.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("master")
public class NoticeController {
    private final NoticeService noticeService;

    /**
     * 공지사항 생성
     */
    @PostMapping("notice")
    public CMRespDto<?> createNotice(@ModelAttribute CreateNoticeRequestDto createNoticeRequestDto) {
        return noticeService.createNotice(createNoticeRequestDto);
    }

    /**
     * 공지사항 조회
     * @param id    조회할 공지사항 id
     */
    @GetMapping("notice")
    public ResponseEntity<Notice> getNotice(@RequestParam Long id) {
        return ResponseEntity.ok(noticeService.getNotice(id));
    }

    /**
     * 공지사항 전체 조회
     */
    @GetMapping("notice/all")
    public ResponseEntity<List<Notice>> getAllNotice(String search) {
        return ResponseEntity.ok(noticeService.getAllNotice(search));
    }

    /**
     * 공지사항 수정
     * @param id        수정할 공지사항 id
     * @param notice    공지사항 정보
     */
    @PutMapping("notice")
    public ResponseEntity<Notice> editNotice(@RequestParam Long id, @RequestBody Notice notice) {
        return ResponseEntity.ok(noticeService.editNotice(id, notice));
    }

    /**
     * 공지사항 삭제
     * @param id    삭제할 공지사항 id
     */
    @DeleteMapping("notice")
    public String deleteNotice(@RequestParam Long id) {
        noticeService.deleteNotice(id);
        return "notice deleted";
    }

    /**
     * 공지사항 임시저장
     */
    @PostMapping("notice/draft")
    public CMRespDto<?> draftNotice(@ModelAttribute CreateNoticeRequestDto createNoticeRequestDto) {
        return noticeService.draftNotice(createNoticeRequestDto);
    }
}
