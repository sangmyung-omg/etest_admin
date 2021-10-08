package com.tmax.eTest.Admin.notice.controller;

import com.tmax.eTest.Admin.notice.service.AdminNoticeService;
import com.tmax.eTest.Common.model.support.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("master")
public class AdminNoticeController {
    private final AdminNoticeService adminNoticeService;

    /**
     * 공지사항 생성
     * @param notice    공지사항 정보
     */
    @PostMapping("notice")
    public ResponseEntity<Notice> createNotice(@RequestBody Notice notice) {
        return ResponseEntity.ok(adminNoticeService.createNotice(notice));
    }

    /**
     * 공지사항 조회
     * @param id    조회할 공지사항 id
     */
    @GetMapping("notice")
    public ResponseEntity<Notice> getNotice(@RequestParam Long id) {
        return ResponseEntity.ok(adminNoticeService.getNotice(id));
    }

    /**
     * 공지사항 전체 조회
     */
    @GetMapping("notice/all")
    public ResponseEntity<List<Notice>> getAllNotice(String search) {
        return ResponseEntity.ok(adminNoticeService.getAllNotice(search));
    }

    /**
     * 공지사항 수정
     * @param id        수정할 공지사항 id
     * @param notice    공지사항 정보
     */
    @PutMapping("notice")
    public ResponseEntity<Notice> editNotice(@RequestParam Long id, @RequestBody Notice notice) {
        return ResponseEntity.ok(adminNoticeService.editNotice(id, notice));
    }

    /**
     * 공지사항 삭제
     * @param id    삭제할 공지사항 id
     */
    @DeleteMapping("notice")
    public String deleteNotice(@RequestParam Long id) {
        adminNoticeService.deleteNotice(id);
        return "notice deleted";
    }
}
