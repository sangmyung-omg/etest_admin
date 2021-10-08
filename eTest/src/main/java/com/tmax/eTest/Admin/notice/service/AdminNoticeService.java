package com.tmax.eTest.Admin.notice.service;

import com.tmax.eTest.Admin.notice.repository.AdminNoticeRepository;
import com.tmax.eTest.Admin.notice.repository.AdminNoticeRepositorySupport;
import com.tmax.eTest.Admin.util.ColumnNullPropertiesHandler;
import com.tmax.eTest.Common.model.support.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminNoticeService {
    private final AdminNoticeRepository adminNoticeRepository;
    private final AdminNoticeRepositorySupport adminNoticeRepositorySupport;

    public Notice createNotice(Notice notice) {
        Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());
        if (notice.getDraft() == 0)
            notice.setDateAdd(currentDateTime);
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
        ColumnNullPropertiesHandler.copyNonNullProperties(newNotice, notice);
        notice.setDateEdit(currentDateTime);
        return adminNoticeRepository.save(notice);
    }

    public void deleteNotice(Long id) {
        adminNoticeRepository.deleteById(id);
    }

    public void updateNoticeViews(Long id) {
        Notice notice = getNotice(id);
        notice.setViews(notice.getViews() + 1);
        adminNoticeRepository.save(notice);
    }
}
