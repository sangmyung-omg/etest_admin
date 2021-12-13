package com.tmax.eTest.Admin.notice.service;

import com.tmax.eTest.Admin.notice.dto.CreateNoticeRequestDto;
import com.tmax.eTest.Admin.notice.repository.NoticeRepository;
import com.tmax.eTest.Admin.notice.repository.NoticeRepositorySupport;
import com.tmax.eTest.Admin.util.ColumnNullPropertiesHandler;
import com.tmax.eTest.Auth.dto.CMRespDto;
import com.tmax.eTest.Common.model.support.Notice;
import com.tmax.eTest.Push.dto.CategoryPushRequestDTO;
import com.tmax.eTest.Push.service.PushService;
import lombok.RequiredArgsConstructor;
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
public class NoticeService extends PushService {
    private final NoticeRepository noticeRepository;
    private final NoticeRepositorySupport noticeRepositorySupport;

    @Value("${file.path}")
    private String rootPath;

    @Transactional
    public CMRespDto<?> createNotice(CreateNoticeRequestDto createNoticeRequestDto) {
        String noticeImageFolderURL = rootPath + "notice/";
        Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());
        Notice notice = null;
        if (createNoticeRequestDto.getImage() != null) {
            String imageName = UUID.randomUUID() + "_" + createNoticeRequestDto.getImage().getOriginalFilename();
            String imageUrlString = noticeImageFolderURL + imageName;
            Path imageUrlPath = Paths.get(imageUrlString);
            notice =
                    Notice.builder()
                            .title(createNoticeRequestDto.getTitle())
                            .content(createNoticeRequestDto.getContent())
                            .draft(0)
                            .views((long) 0)
                            .dateAdd(currentDateTime)
                            .dateEdit(currentDateTime)
                            .imageUrl(imageUrlString)
                            .build();
            try {
                Files.write(imageUrlPath, createNoticeRequestDto.getImage().getBytes());
            } catch (Exception e) {
                throw new IllegalArgumentException("notice image save error");
            }
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
        categoryPushRequest(CategoryPushRequestDTO.builder()
                .category("notice")
                .title("공지사항")
                .body(notice.getTitle())
                .build())
                .block();
        return new CMRespDto<>(200, "success", notice);
    }

    @Transactional
    public CMRespDto<?> draftNotice(CreateNoticeRequestDto createNoticeRequestDto) {
        String noticeImageFolderURL = rootPath + "notice/";
        Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());
        Notice notice = null;
        if (createNoticeRequestDto.getImage() != null) {
            String imageName = UUID.randomUUID() + "_" + createNoticeRequestDto.getImage().getOriginalFilename();
            String imageUrlString = noticeImageFolderURL + imageName;
            Path imageUrlPath = Paths.get(imageUrlString);
            notice =
                    Notice.builder()
                            .title(createNoticeRequestDto.getTitle())
                            .content(createNoticeRequestDto.getContent())
                            .draft(1)
                            .views((long) 0)
                            .dateAdd(currentDateTime)
                            .dateEdit(currentDateTime)
                            .imageUrl(imageUrlString)
                            .build();
            try {
                Files.write(imageUrlPath, createNoticeRequestDto.getImage().getBytes());
            } catch (Exception e) {
                throw new IllegalArgumentException("notice image save error");
            }
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

    public Notice editNotice(Long id, Notice newNotice) {
        Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());
        Notice notice = getNotice(id);
        if (notice == null) {
            throw new IllegalArgumentException("notice is null");
        }
        ColumnNullPropertiesHandler.copyNonNullProperties(newNotice, notice);
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
