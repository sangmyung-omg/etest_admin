package com.tmax.eTest.Support.notice.service;

import com.tmax.eTest.Auth.dto.CMRespDto;
import com.tmax.eTest.Common.model.support.Notice;
import com.tmax.eTest.Push.dto.PushRequestDTO;
import com.tmax.eTest.Push.service.PushService;
import com.tmax.eTest.Support.notice.dto.CreateNoticeRequestDto;
import com.tmax.eTest.Support.notice.repository.NoticeRepository;
import com.tmax.eTest.Support.notice.repository.NoticeRepositorySupport;
import lombok.RequiredArgsConstructor;
//import org.apache.commons.codec.binary.Base64;
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
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoticeService extends PushService {
    private final NoticeRepository noticeRepository;
    private final NoticeRepositorySupport noticeRepositorySupport;
    private Logger logger = LoggerFactory.getLogger(NoticeService.class);

    @Value("${file.path}")
    private String rootPath;

    public ResponseEntity<Resource> getNoticeImage(String fileName) {
        String filePathString = rootPath + "/notice/";
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
    public CMRespDto<?> createNotice(CreateNoticeRequestDto createNoticeRequestDto) throws Exception {
        Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());
        Notice notice = null;
        if (createNoticeRequestDto.getImage() != null) {
            java.util.Base64.Encoder encoder = Base64.getEncoder();
            byte[] encodedBytes = encoder.encode(createNoticeRequestDto.getImage().getBytes());
            String imageEncoding = new String(encodedBytes);
            notice =
                    Notice.builder()
                            .title(createNoticeRequestDto.getTitle())
                            .content(createNoticeRequestDto.getContent())
                            .draft(0)
                            .views((long) 0)
                            .dateAdd(currentDateTime)
                            .dateEdit(currentDateTime)
                            .imageEncoding(imageEncoding)
                            .build();
            noticeRepository.save(notice);
        }
        if (createNoticeRequestDto.getImage() == null) {
            notice = Notice.builder()
                    .title(createNoticeRequestDto.getTitle())
                    .content(createNoticeRequestDto.getContent())
                    .draft(0)
                    .views((long) 0)
                    .dateAdd(currentDateTime)
                    .dateEdit(currentDateTime)
                    .build();
            noticeRepository.save(notice);
        }
        try {
            categoryPushRequest(PushRequestDTO.builder()
                    .category("notice")
                    .title("공지사항")
                    .body(notice.getTitle())
                    .url("/help?type=notice")
                    .build())
                    .block();
        }
        catch (Exception e) {
            logger.error("Push request failed. Check server status and/or 'push-admin-config.properties'.");
            return new CMRespDto<>(201, "push failed", notice);
        }
        return new CMRespDto<>(200, "success", notice);
    }

    @Transactional
    public CMRespDto<?> draftNotice(CreateNoticeRequestDto createNoticeRequestDto) throws IOException {
        String noticeImageFolderURL = rootPath + "notice/";
        Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());
        Notice notice = null;
        if (createNoticeRequestDto.getImage() != null) {
            java.util.Base64.Encoder encoder = Base64.getEncoder();
            byte[] encodedBytes = encoder.encode(createNoticeRequestDto.getImage().getBytes());
            String imageEncoding = new String(encodedBytes);
            notice =
                    Notice.builder()
                            .title(createNoticeRequestDto.getTitle())
                            .content(createNoticeRequestDto.getContent())
                            .draft(1)
                            .views((long) 0)
                            .dateAdd(currentDateTime)
                            .dateEdit(currentDateTime)
                            .imageEncoding(imageEncoding)
                            .build();
            noticeRepository.save(notice);
        }
        if (createNoticeRequestDto.getImage() == null) {
            notice = Notice.builder()
                    .title(createNoticeRequestDto.getTitle())
                    .content(createNoticeRequestDto.getContent())
                    .draft(1)
                    .views((long) 0)
                    .dateAdd(currentDateTime)
                    .dateEdit(currentDateTime)
                    .build();
            noticeRepository.save(notice);
        }
        return new CMRespDto<>(200, "success", notice);
    }

    public Notice getNotice(Long id) {
        if (noticeRepository.findById(id).isPresent())
            return noticeRepository.findById(id).get();
        return null;
    }

    public List<Notice> getAllNotice(String search) {
        return noticeRepositorySupport.noticeList(search);
    }

    public Notice editNotice(Long id, CreateNoticeRequestDto createNoticeRequestDto) throws IOException {
        Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());
        Notice notice = getNotice(id);
        if (notice == null)
            throw new IllegalArgumentException("notice is null");
        notice.setDraft(createNoticeRequestDto.getDraft());
        notice.setTitle(createNoticeRequestDto.getTitle());
        notice.setContent(createNoticeRequestDto.getContent());
        if (createNoticeRequestDto.getImage() != null){
            java.util.Base64.Encoder encoder = Base64.getEncoder();
            byte[] encodedBytes = encoder.encode(createNoticeRequestDto.getImage().getBytes());
            String imageEncoding = new String(encodedBytes);
            notice.setImageEncoding(imageEncoding);
        }
        notice.setDateEdit(currentDateTime);
        return noticeRepository.save(notice);
    }

    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }

    public void updateNoticeViews(Long id) {
        Notice notice = getNotice(id);
        if (notice == null) {
            throw new IllegalArgumentException("notice is null");
        }
        notice.setViews(notice.getViews() + 1);
        noticeRepository.save(notice);
    }
}
