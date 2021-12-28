package com.tmax.eTest.Admin.CustomerSupport.service;


import com.tmax.eTest.Common.model.support.Inquiry;
import com.tmax.eTest.Admin.CustomerSupport.model.dto.InquiryDTO;
import com.tmax.eTest.Admin.CustomerSupport.model.dto.InquiryFileDTO;
import com.tmax.eTest.Admin.CustomerSupport.repository.InquiryRepository;
import com.tmax.eTest.Common.model.support.Notice;
import com.tmax.eTest.Push.dto.CategoryPushRequestDTO;
import com.tmax.eTest.Push.service.PushService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("CustomerSupportInquiryService")
public class InquiryService extends PushService {

    @Autowired
    InquiryRepository inquiryRepository;

    public List<InquiryDTO> getInquiryList(){

        //add sorting
        List<Inquiry> inquiryList= inquiryRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));

        //TODO adminnickname fix
        return inquiryList.stream()
                .map(i ->
                    InquiryDTO.builder()
                    .inquiryId(i.getId())
                    .userNickname(i.getUserMaster() == null ? "삭제된유저입니다" : i.getUserMaster().getNickname())
                    .type(i.getType())
                    .title(i.getTitle())
                    .lastUpdated(i.getCreateDate())
                    .status(i.getStatus())
                    .answerDate(i.getAnswer_time())
                    .adminNickname(i.getAdminUuid())
                    .build())
                .collect(Collectors.toList());
    }

    public InquiryDTO getInquiryDetails(Long id){

        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Inquiry with Given Id"));

        return InquiryDTO.builder()
                .inquiryId(inquiry.getId())
                .type(inquiry.getType())
                .userNickname(inquiry.getUserMaster() == null ? "삭제된유저입니다" : inquiry.getUserMaster().getNickname())
                .title(inquiry.getTitle())
                .lastUpdated(inquiry.getCreateDate())
                .status(inquiry.getStatus())
                .content(inquiry.getContent())
                .answer(inquiry.getAnswer())
                .answerDate(inquiry.getAnswer_time())
                .inquiryFile(inquiry.getInquiry_file().stream()
                        .map(i -> InquiryFileDTO.builder()
                                .id(i.getId())
                                .url(i.getUrl())
                                .type(i.getType())
                                .name(i.getName())
                                .size(i.getSize())
                                .build()).collect(Collectors.toList()))
                .adminNickname(inquiry.getAdminUuid())
                .build();
    }

    public InquiryDTO answerInquiry(Long id, String admin_uuid, String admin_nickname, InquiryAnswerDTO inquiryAnswerDTO){
        if (StringUtils.isEmpty(inquiryAnswerDTO.getAnswer())){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Answer cannot be null or emtpy");
        }
        if (inquiryAnswerDTO.getDraft() == null){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Draft cannot be null or emtpy");
        }

        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Inquiry with Given Id"));

        inquiry.setAdminUuid(admin_uuid);
        inquiry.setAnswer(inquiryAnswerDTO.getAnswer());

        if (inquiryAnswerDTO.getDraft() == 1){
            inquiry.setStatus("임시저장");
        }
        else if (inquiryAnswerDTO.getDraft() == 0){
            inquiry.setStatus("답변완료");
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Draft must be 0 or 1");
        }

        inquiry.setAnswer_time(LocalDateTime.now());

        inquiryRepository.save(inquiry);
        List<String> userUuidList = new ArrayList<>();
        userUuidList.add(inquiry.getUserMaster().getUserUuid());
        categoryPushRequestByUserUuid(CategoryPushRequestDTO.builder()
                .category("inquiry")
                .title("1:1문의")
                .userUuid(userUuidList)
                .body("1:1 문의에 대한 답변을 확인해보세요.")
                .build())
                .block();
        return InquiryDTO.builder()
                .inquiryId(inquiry.getId())
                .type(inquiry.getType())
                .userNickname(inquiry.getUserMaster() == null ? "삭제된유저입니다" : inquiry.getUserMaster().getNickname())
                .title(inquiry.getTitle())
                .lastUpdated(inquiry.getAnswer_time())
                .status(inquiry.getStatus())
                .content(inquiry.getContent())
                .answer(inquiry.getAnswer())
                .answerDate(inquiry.getAnswer_time())
                .inquiryFile(inquiry.getInquiry_file().stream()
                        .map(i -> InquiryFileDTO.builder()
                                .id(i.getId())
                                .url(i.getUrl())
                                .type(i.getType())
                                .name(i.getName())
                                .size(i.getSize())
                                .build()).collect(Collectors.toList()))
                .adminNickname(admin_nickname)
                .build();
    }

    public void deleteInquiry(Long id) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Inquiry with Given Id"));
        inquiryRepository.delete(inquiry);
    }
}
