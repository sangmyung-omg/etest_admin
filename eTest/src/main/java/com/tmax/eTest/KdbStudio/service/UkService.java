package com.tmax.eTest.KdbStudio.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmax.eTest.Common.model.uk.UkDescriptionVersion;
import com.tmax.eTest.Common.model.uk.UkDesriptionVersionCompositeKey;
import com.tmax.eTest.Common.model.video.Video;
import com.tmax.eTest.Common.model.video.VideoUkRel;
import com.tmax.eTest.Common.repository.uk.UkDescriptionVersionRepo;
import com.tmax.eTest.KdbStudio.dto.UkGetOutputDTO;
import com.tmax.eTest.KdbStudio.dto.UkRelatedArticleDTO;
import com.tmax.eTest.KdbStudio.dto.UkRelatedVideoDTO;
import com.tmax.eTest.KdbStudio.dto.UkUpdateDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UkService {
    
    @Autowired
    UkDescriptionVersionRepo ukVersionRepo;

    public List<UkGetOutputDTO> getUkInfo(Integer versionId) {
        List<UkGetOutputDTO> ukList = new ArrayList<UkGetOutputDTO>();
        
        // List<UkDescriptionVersion> allUkList = ukVersionRepo.findByVersionIdOrderByUkId(new Long(versionId));
        List<UkDescriptionVersion> allUkList = ukVersionRepo.findByVersionIdFetchJoin(new Long(versionId));

        for (UkDescriptionVersion ukInfo : allUkList) {
            List<UkRelatedVideoDTO> videoList = new ArrayList<UkRelatedVideoDTO>();
            List<UkRelatedArticleDTO> articleList = new ArrayList<UkRelatedArticleDTO>();

            // basic uk info
            UkGetOutputDTO template = UkGetOutputDTO.builder().ukId(ukInfo.getUkId().intValue())
                                                                .ukName(ukInfo.getUkName())
                                                                .partName(ukInfo.getUkMaster().getPart())
                                                                .ukDescription(ukInfo.getUkDescription())
                                                                .externalLink(ukInfo.getExternalLink())
                                                                .updateDate(ukInfo.getEditDate())
                                                                .page(ukInfo.getPage())
                                                                .build();
            if (template.getUkId() % 100 == 0) 
                log.debug("Gathering info of DTO uk_id : " + Integer.toString(template.getUkId()));
            // video & article info
            for (VideoUkRel video : ukInfo.getUkMaster().getVideoUks()) {
                if (video.getVideo().getType().equalsIgnoreCase("VIDEO")) {
                    Video videoObj = video.getVideo();
                    UkRelatedVideoDTO videoInfo = UkRelatedVideoDTO.builder().videoId(video.getVideoId())
                                                                                .videoSrc(videoObj.getVideoSrc())
                                                                                .title(videoObj.getTitle())
                                                                                .imgSrc(videoObj.getImgSrc())
                                                                                .totalTime(videoObj.getTotalTime())
                                                                                .views(videoObj.getVideoHit().getHit())
                                                                                .build();
                    videoList.add(videoInfo);
                } else if (video.getVideo().getType().equalsIgnoreCase("ARTICLE")) {
                    Video articleObj = video.getVideo();
                    UkRelatedArticleDTO articleInfo = UkRelatedArticleDTO.builder().articleId(video.getVideoId())
                                                                                    .articleSrc(articleObj.getVideoSrc())
                                                                                    .title(articleObj.getTitle())
                                                                                    .imgSrc(articleObj.getImgSrc())
                                                                                    .totalTime(articleObj.getTotalTime())
                                                                                    .views(articleObj.getVideoHit().getHit())
                                                                                    .build();
                    articleList.add(articleInfo);
                }
            }

            template.setRelatedVideo(videoList);
            template.setRelatedArticle(articleList);

            ukList.add(template);
        }
        log.debug("Completed gathering uk infos : " + Integer.toString(allUkList.get(allUkList.size()-1).getUkId().intValue()));
        
        return ukList;
    }

    public Map<String, Object> getAllPagedUkInfoForVersion(Integer versionId, Integer page, Integer size) {
        Map<String, Object> result = new HashMap<String, Object>();

        List<UkGetOutputDTO> ukList = new ArrayList<UkGetOutputDTO>();

        // 페이징 조건 설정
        Pageable pageAndSize = PageRequest.of(page, size, Sort.by("ukId").ascending());

        log.debug("Getting uk version infos......");
        Page<UkDescriptionVersion> pages = ukVersionRepo.findByVersionId(new Long(versionId), pageAndSize);
        log.debug(Long.toString(pages.getTotalElements()));
        // 선정한 페이지에 속하는 UK 정보들 (: target infos)
        List<UkDescriptionVersion> ukQueryResult = pages.getContent();
        // 페이지 관련 추가 변수들 (: paging infos)
        Integer max_page_num = pages.getTotalPages();
        Integer current_page = pages.getNumber();
        Integer page_size = pages.getSize();
        Long total_elements_num = pages.getTotalElements();
        
        log.debug("ukList : " + Integer.toString(ukQueryResult.size()));

        // 조회해온 특정 페이지의 UK들을 output 형식에 맞게 가공
        for (UkDescriptionVersion ukInfo : ukQueryResult) {
            List<UkRelatedVideoDTO> videoList = new ArrayList<UkRelatedVideoDTO>();
            List<UkRelatedArticleDTO> articleList = new ArrayList<UkRelatedArticleDTO>();

            // basic uk info
            UkGetOutputDTO template = UkGetOutputDTO.builder().ukId(ukInfo.getUkId().intValue())
                                                                .ukName(ukInfo.getUkName())
                                                                .partName(ukInfo.getUkMaster().getPart())
                                                                .ukDescription(ukInfo.getUkDescription())
                                                                .externalLink(ukInfo.getExternalLink())
                                                                .updateDate(ukInfo.getEditDate())
                                                                .build();
            log.debug("DTO uk_id : " + Integer.toString(template.getUkId()));
            // video & article info
            for (VideoUkRel video : ukInfo.getUkMaster().getVideoUks()) {
                if (video.getVideo().getType().equalsIgnoreCase("VIDEO")) {
                    Video videoObj = video.getVideo();
                    UkRelatedVideoDTO videoInfo = UkRelatedVideoDTO.builder().videoId(video.getVideoId())
                                                                                .videoSrc(videoObj.getVideoSrc())
                                                                                .title(videoObj.getTitle())
                                                                                .imgSrc(videoObj.getImgSrc())
                                                                                .totalTime(videoObj.getTotalTime())
                                                                                .views(videoObj.getVideoHit().getHit())
                                                                                .build();
                    videoList.add(videoInfo);
                } else if (video.getVideo().getType().equalsIgnoreCase("ARTICLE")) {
                    Video articleObj = video.getVideo();
                    UkRelatedArticleDTO articleInfo = UkRelatedArticleDTO.builder().articleId(video.getVideoId())
                                                                                    .articleSrc(articleObj.getVideoSrc())
                                                                                    .title(articleObj.getTitle())
                                                                                    .imgSrc(articleObj.getImgSrc())
                                                                                    .totalTime(articleObj.getTotalTime())
                                                                                    .views(articleObj.getVideoHit().getHit())
                                                                                    .build();
                    articleList.add(articleInfo);
                }
            }

            template.setRelatedVideo(videoList);
            template.setRelatedArticle(articleList);

            ukList.add(template);
        }

        // construct output format
        result.put("ukList", ukList);
        result.put("maxPageNum", max_page_num);
        result.put("currentPage", current_page);
        result.put("pageSize", page_size);
        result.put("totalElementsNum", total_elements_num);

        return result;
    }

    public String updateUkInfo(Long ukId, Long versionId, String ukName, String ukDescription) throws NotFoundException {
        // get existing uk info
        UkDesriptionVersionCompositeKey keyset = new UkDesriptionVersionCompositeKey();
        keyset.setUkId(ukId);
        keyset.setVersionId(versionId);
        UkDescriptionVersion ukInfo = ukVersionRepo.findById(keyset).orElseThrow(() -> new NotFoundException("error : The version id given is not found, " + Long.toString(versionId)));

        // update uk info
        if (ukName != null)
            ukInfo.setUkName(ukName);
        if (ukDescription != null)
            ukInfo.setUkDescription(ukDescription);
        ukVersionRepo.save(ukInfo);

        return "Successfully updated Uk info.";
    }

    public String updateUkInfo(Long ukId, Long versionId, UkUpdateDTO input) throws NotFoundException {
        // get existing uk info
        UkDesriptionVersionCompositeKey keyset = new UkDesriptionVersionCompositeKey();
        keyset.setUkId(ukId);
        keyset.setVersionId(versionId);
        UkDescriptionVersion ukInfo = ukVersionRepo.findById(keyset).orElseThrow(() -> new NotFoundException("error : The version id given is not found, " + Long.toString(versionId)));

        // update uk info
        ukInfo.setUkName(input.getUkName());
        if (input.getUkDescription() != null && !input.getUkDescription().equalsIgnoreCase(""))
            ukInfo.setUkDescription(input.getUkDescription());
        ukVersionRepo.save(ukInfo);

        return "Successfully updated Uk info.";
    }
}
