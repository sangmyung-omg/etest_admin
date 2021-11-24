package com.tmax.eTest.KdbStudio.service;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UkService {
    
    @Autowired
    UkDescriptionVersionRepo ukVersionRepo;

    public List<UkGetOutputDTO> getAllUkInfoForVersion(Integer versionId) {
        List<UkGetOutputDTO> result = new ArrayList<UkGetOutputDTO>();

        List<UkDescriptionVersion> ukList = ukVersionRepo.findByVersionIdOrderByUkId(new Long(versionId));
        log.info("ukList : " + Integer.toString(ukList.size()));

        for (UkDescriptionVersion ukInfo : ukList) {
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
            log.info("DTO uk_id : " + Integer.toString(template.getUkId()));
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

            result.add(template);
        }
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
