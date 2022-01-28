package com.tmax.eTest.Support.notice.controller;

import com.tmax.eTest.Support.notice.dto.CreateNoticeRequestDto;
import com.tmax.eTest.Support.notice.service.NoticeService;
import com.tmax.eTest.Auth.dto.CMRespDto;
import com.tmax.eTest.Common.model.support.Notice;
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
public class NoticeController {
    private final NoticeService noticeService;

    @Value("${file.path}")
    private String rootPath;

    @GetMapping(value = "/notice/image")
    public ResponseEntity<Resource> getNoticeImage(@Param("fileName")String fileName ) {
        HttpHeaders header = new HttpHeaders();
        Path imageUrl = Paths.get(rootPath + "/notice/" + fileName);

        Tika tika = new Tika();
        String mimeType;
        try {
            mimeType = tika.detect(imageUrl);
            header.add("Content-Type", mimeType);
        } catch (IOException e) {
            throw new IllegalArgumentException("mime type error");
        }
        return noticeService.getNoticeImage(fileName);
    }

    /**
     * 공지사항 생성
     */
    @PostMapping("notice")
    public CMRespDto<?> createNotice(@ModelAttribute CreateNoticeRequestDto createNoticeRequestDto) throws Exception {
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
     */
    @PostMapping("notice/edit")
    public ResponseEntity<Notice> editNotice(@RequestParam Long id, @ModelAttribute CreateNoticeRequestDto createNoticeRequestDto) throws IOException {
        return ResponseEntity.ok(noticeService.editNotice(id, createNoticeRequestDto));
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
    public CMRespDto<?> draftNotice(@ModelAttribute CreateNoticeRequestDto createNoticeRequestDto) throws IOException {
        return noticeService.draftNotice(createNoticeRequestDto);
    }
}
