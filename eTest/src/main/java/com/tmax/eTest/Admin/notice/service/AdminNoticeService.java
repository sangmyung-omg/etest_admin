package com.tmax.eTest.Admin.notice.service;

import com.tmax.eTest.Admin.notice.repository.AdminNoticeRepository;
import com.tmax.eTest.Admin.notice.repository.AdminNoticeRepositorySupport;
import com.tmax.eTest.Admin.util.ColumnNullPropertiesHandler;
import com.tmax.eTest.Common.model.support.Notice;
import com.tmax.eTest.Push.dto.CategoryPushRequestDTO;
import com.tmax.eTest.Push.service.PushService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminNoticeService extends PushService {
    private final AdminNoticeRepository adminNoticeRepository;
    private final AdminNoticeRepositorySupport adminNoticeRepositorySupport;

    public Notice createNotice(Notice notice) {
        Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());
        notice.setDraft(0);
        notice.setViews((long) 0);
        notice.setDateAdd(currentDateTime);
        adminNoticeRepository.save(notice);
        categoryPushRequest(CategoryPushRequestDTO.builder()
                .category("notice")
                .title("공지사항")
                .body(notice.getTitle())
                .build())
                .block();
        return notice;
    }

    public Notice draftNotice(Notice notice) {
        notice.setDraft(1);
        return adminNoticeRepository.save(notice);
    }

    public Notice getNotice(Long id) {
        if (adminNoticeRepository.findById(id).isPresent())
            return adminNoticeRepository.findById(id).get();
        return null;
    }

    public List<Notice> getAllNotice(String search) {
        return adminNoticeRepositorySupport.noticeList(search);
    }

    public Notice editNotice(Long id, Notice newNotice) {
        Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());
        Notice notice = getNotice(id);
        if (notice == null) {
            throw new IllegalArgumentException("notice is null");
        }
        ColumnNullPropertiesHandler.copyNonNullProperties(newNotice, notice);
        notice.setDateEdit(currentDateTime);
        return adminNoticeRepository.save(notice);
    }

    public void deleteNotice(Long id) {
        adminNoticeRepository.deleteById(id);
    }

    public void updateNoticeViews(Long id) {
        Notice notice = getNotice(id);
        if (notice == null) {
            throw new IllegalArgumentException("notice is null");
        }
        notice.setViews(notice.getViews() + 1);
        adminNoticeRepository.save(notice);
    }
}
