package com.tmax.eTest.Contents.service;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmax.eTest.Common.model.meta.MetaCodeMaster;
import com.tmax.eTest.Common.model.uk.UkMaster;
import com.tmax.eTest.Common.model.video.Hashtag;
import com.tmax.eTest.Common.model.video.Video;
import com.tmax.eTest.Common.model.video.VideoBookmark;
import com.tmax.eTest.Common.model.video.VideoCurriculum;
import com.tmax.eTest.Common.model.video.VideoHashtag;
import com.tmax.eTest.Common.model.video.VideoHit;
import com.tmax.eTest.Common.model.video.VideoUkRel;
import com.tmax.eTest.Common.repository.uk.UkMasterRepo;
import com.tmax.eTest.Common.repository.video.HashtagRepository;
import com.tmax.eTest.Common.repository.video.VideoCurriculumRepository;
import com.tmax.eTest.Common.repository.video.VideoRepository;
import com.tmax.eTest.Contents.dto.CodeDTO;
import com.tmax.eTest.Contents.dto.CodeSet;
import com.tmax.eTest.Contents.dto.ListDTO;
import com.tmax.eTest.Contents.dto.SizeDTO;
import com.tmax.eTest.Contents.dto.SortType;
import com.tmax.eTest.Contents.dto.SuccessDTO;
import com.tmax.eTest.Contents.dto.VideoCreateDTO;
import com.tmax.eTest.Contents.dto.VideoCurriculumDTO;
import com.tmax.eTest.Contents.dto.VideoDTO;
import com.tmax.eTest.Contents.dto.VideoJoin;
import com.tmax.eTest.Contents.exception.ContentsException;
import com.tmax.eTest.Contents.exception.ErrorCode;
import com.tmax.eTest.Contents.repository.support.MetaCodeMasterRepositorySupport;
import com.tmax.eTest.Contents.repository.support.VideoCurriculumRepositorySupport;
import com.tmax.eTest.Contents.repository.support.VideoRepositorySupport;
import com.tmax.eTest.Contents.util.CommonUtils;
import com.tmax.eTest.KdbStudio.util.UkVersionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VideoService {

  // public enum VideoType {
  // YOUTUBE(""), SELF(""), VIDEO("콘텐츠 종류CV"), ARTICLE("콘텐츠 종류CW");

  // private final String value;

  // VideoType(String value) {
  // this.value = value;
  // }

  // public String value() {
  // return value;
  // }
  // }
  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  public enum VideoType {
    YOUTUBE("CV", "20"), SELF("CV", "10"), VIDEO, ARTICLE("CW", "20");

    private String type;
    private String origin;

    public static VideoType findBy(String str) {
      for (VideoType videoType : values()) {
        if (str.equals(videoType.name()))
          return videoType;
      }
      return null;
    }

    public static String classify(String str) {
      if (str.equals(YOUTUBE.name()) || str.equals(SELF.name()))
        return "VIDEO";
      else
        return "ARTICLE";
    }

  }

  private final String YOUTUBE_TYPE = "youtu";

  @Autowired
  private VideoCurriculumRepositorySupport videoCurriculumRepositorySupport;

  @Autowired
  private VideoCurriculumRepository videoCurriculumRepository;

  @Autowired
  private VideoRepositorySupport videoRePositorySupport;

  @Autowired
  private VideoRepository videoRepository;

  @Autowired
  private UkMasterRepo ukMasterRepo;

  @Autowired
  private HashtagRepository hashtagRepository;

  @Autowired
  private MetaCodeMasterRepositorySupport metaCodeMasterRepositorySupport;

  @Autowired
  private CommonUtils commonUtils;

  @Autowired
  private UkVersionManager ukVersionManager;

  public ListDTO.Curriculum getVideoCurriculumList() {
    List<VideoCurriculum> curriculums = videoCurriculumRepositorySupport.findAll();
    return convertVideoCurriculumToDTO(curriculums);
  }

  public VideoDTO getVideo(String videoId) {
    Video video = videoRepository.findById(videoId).orElseThrow(
        () -> new ContentsException(ErrorCode.DB_ERROR, "VideoId doesn't exist in Video Table"));
    return convertVideoToDTO(video);
  }

  public ListDTO.Video getVideoList(Long curriculumId, SortType sort, String keyword) {
    List<Video> videos = videoRePositorySupport.findVideosByCurriculum(curriculumId, sort, keyword);
    return convertVideoToDTO(videos);
  }

  public ListDTO.Video getVideoList(Long curriculumId, SortType sort, String keyword, Integer page, Integer size) {
    List<Video> videos = videoRePositorySupport.findVideosByCurriculumByPage(curriculumId, sort, keyword, page, size);
    return convertVideoToDTO(videos);
  }

  public SizeDTO getVideoSize() {
    return new SizeDTO(videoRePositorySupport.findVideoSize());
  }

  private Video makeVideo(VideoCreateDTO videoCreateDTO, String videoId) {
    VideoType videoType = VideoType.findBy(videoCreateDTO.getVideoType());
    VideoCurriculum videoCurriculum = videoCurriculumRepository.findByCodeId(videoCreateDTO.getGroupArea());
    Long curriculumId = videoCurriculum.getCurriculumId();
    BigInteger sequence = makeSequence(videoCreateDTO, videoType);
    String type = VideoType.classify(videoCreateDTO.getVideoType());
    String codeSet;
    try {
      codeSet = makeCodeSet(videoCreateDTO, videoType);
    } catch (JsonProcessingException e) {
      throw new ContentsException(ErrorCode.CODE_ERROR);
    }
    String videoSrc = makeVideoSrc(videoCreateDTO.getVideoSrc());

    Video video = Video.builder().videoId(videoId).videoSrc(videoSrc)
        .title(videoCreateDTO.getTitle()).imgSrc(videoCreateDTO.getImgSrc()).curriculumId(curriculumId)
        .totalTime(videoCreateDTO.getTotalTime()).startTime(videoCreateDTO.getStartTime())
        .endTime(videoCreateDTO.getEndTime()).createDate(videoCreateDTO.getCreateDate())
        .registerDate(videoCreateDTO.getRegisterDate()).endDate(videoCreateDTO.getEndDate()).sequence(sequence)
        .codeSet(codeSet).type(type).related(videoCreateDTO.getRelated()).show(videoCreateDTO.getShow())
        .videoCurriculum(videoCurriculum).build();
    List<UkMaster> uks = getUks(videoCreateDTO.getUkIds());
    for (UkMaster uk : uks) {
      VideoUkRel videoUk = VideoUkRel.builder().videoId(videoId).ukId(Long.valueOf(uk.getUkId())).ukMaster(uk).build();
      video.addUk(videoUk);
    }
    List<Hashtag> hashtags = getHashtags(videoCreateDTO.getHashtags());
    for (Hashtag hashtag : hashtags) {
      VideoHashtag videoHashtag = VideoHashtag.builder().videoId(videoId).hashtagId(hashtag.getHashtagId())
          .hashtag(hashtag).build();
      video.addHashTag(videoHashtag);
    }
    return video;
  }

  private String makeVideoSrc(String url) {
    final String youtubeEmbededStr = "https://www.youtube.com/embed/";
    if (url.contains("youtu.be")) {
      String[] temp = url.split("/");
      return youtubeEmbededStr + temp[temp.length - 1];
    } else
      return url;
  }

  private Video makeVideo(VideoCreateDTO videoCreateDTO, String videoId, VideoHit prevVideoHit,
      Set<VideoBookmark> prevVideoBookmarks) {
    Video video = makeVideo(videoCreateDTO, videoId);
    VideoHit videoHit = VideoHit.builder().video(video).videoId(videoId).hit(prevVideoHit.getHit()).build();
    Set<VideoBookmark> videoBookmarks = new LinkedHashSet<VideoBookmark>();
    prevVideoBookmarks.stream().forEach(e -> videoBookmarks
        .add(VideoBookmark.builder().video(video).videoId(videoId).userUuid(e.getUserUuid()).build()));
    video.setVideoBookmarks(videoBookmarks);
    video.setVideoHit(videoHit);
    return video;
  }

  @Transactional
  public VideoDTO createVideo(VideoCreateDTO videoCreateDTO) {
    String videoId = makeVideoId(videoCreateDTO);
    Video video = makeVideo(videoCreateDTO, videoId);
    VideoHit videoHit = VideoHit.builder().videoId(videoId).hit(0).build();
    video.setVideoHit(videoHit);
    if (videoRepository.existsById(videoId))
      throw new ContentsException(ErrorCode.DB_ERROR, "Code already exists in Video Table");
    else {
      Video create = videoRepository.save(video);
      return convertVideoToDTO(create);
    }
  }

  @Transactional
  public SuccessDTO deleteVideo(String videoId) {
    videoRepository.delete(videoRepository.findById(videoId)
        .orElseThrow(() -> new ContentsException(ErrorCode.DB_ERROR, "VideoId doesn't exist in Video Table")));
    return new SuccessDTO(true);
  }

  @Transactional
  public VideoDTO updateVideo(String videoId, VideoCreateDTO videoCreateDTO) {
    String updateVideoId = makeVideoId(videoCreateDTO);
    Video curVideo = videoRepository.findById(videoId).orElseThrow(
        () -> new ContentsException(ErrorCode.DB_ERROR, "VideoId doesn't exist in Video Table"));
    VideoHit videoHit = curVideo.getVideoHit();
    Set<VideoBookmark> videoBookmarks = curVideo.getVideoBookmarks();
    Video video = null;

    if (videoId.substring(0, videoId.length() -
        9).equals(updateVideoId.substring(0, videoId.length() - 9))) {
      log.info("Code 유지");

      video = makeVideo(videoCreateDTO, videoId, videoHit, videoBookmarks);
    } else {
      log.info("Code 변경 -> Prev Id: " + videoId + " | Cur Id: " + updateVideoId);
      deleteVideo(videoId);
      video = makeVideo(videoCreateDTO, updateVideoId, videoHit, videoBookmarks);
    }
    Video update = videoRepository.save(video);
    return convertVideoToDTO(update);
  }

  public void makeSerialNum(VideoCreateDTO videoCreateDTO) {
    String serialNum = String.format("%09d", Integer.parseInt(videoRepository.findMaxSerialNum()) + 1);
    videoCreateDTO.setSerialNum(serialNum);
  }

  public List<UkMaster> getUks(List<Long> uks) {
    if (commonUtils.objectNullcheck(uks) || uks.size() == 0)
      return new ArrayList<UkMaster>();
    List<Integer> ukIds = uks.stream().map(e -> (int) (long) e).collect(Collectors.toList());
    return ukMasterRepo.findByUkIdIn(ukIds);
  }

  public List<Hashtag> getHashtags(List<String> hashtagNames) {
    if (commonUtils.objectNullcheck(hashtagNames) || hashtagNames.size() == 0)
      return new ArrayList<Hashtag>();
    log.info("TTTT");
    List<Hashtag> hashtags = hashtagRepository.findByNameIn(hashtagNames);
    log.info("TTTT");
    List<String> exists = hashtags.stream().map(e -> e.getName()).collect(Collectors.toList());
    List<Hashtag> addHashtags = hashtagNames.stream().filter(e -> !exists.contains(e))
        .map(e -> Hashtag.builder().name(e).build()).collect(Collectors.toList());
    hashtagRepository.saveAll(addHashtags);
    return Stream.concat(hashtags.stream(), addHashtags.stream()).collect(Collectors.toList());
  }

  public String getCode(String codeId) {
    String temp = codeId.replace(" ", "");
    int count = 0;
    for (char ch : temp.toCharArray()) {
      if (String.valueOf(ch).getBytes().length != 1)
        count++;
      else
        return temp.substring(count);
    }
    throw new ContentsException(ErrorCode.CODE_ERROR, "GET CODE ERROR");
  }

  public String makeVideoId(VideoCreateDTO videoCreateDTO) {
    VideoType videoType = VideoType.findBy(videoCreateDTO.getVideoType());
    makeSerialNum(videoCreateDTO);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(videoCreateDTO.getRegisterDate());
    return getCode(videoType.getType()) + Integer.toString(calendar.get(Calendar.YEAR)) + getCode(videoType.getOrigin())
        + getCode(videoCreateDTO.getClassification()) + getCode(videoCreateDTO.getLargeArea())
        + getCode(videoCreateDTO.getGroupArea()) + getCode(videoCreateDTO.getDetailArea())
        + getCode(videoCreateDTO.getParticleArea()) + videoCreateDTO.getSerialNum();
  }

  public BigInteger makeSequence(VideoCreateDTO videoCreateDTO, VideoType videoType) {
    String seqStr = videoCreateDTO.getRegisterDate().toString().replace("-", "")
        + getCode(videoCreateDTO.getLargeArea()) + getCode(videoCreateDTO.getGroupArea())
        + getCode(videoCreateDTO.getDetailArea()) + getCode(videoCreateDTO.getParticleArea())
        + getCode(videoType.getOrigin()) + getCode(videoCreateDTO.getLevel()) + getCode(videoCreateDTO.getDifficulty())
        + videoCreateDTO.getSerialNum();
    return new BigInteger(seqStr);
  }

  public String makeCodeSet(VideoCreateDTO videoCreateDTO, VideoType videoType) throws JsonProcessingException {
    List<String> codes = Arrays.asList(videoType.getOrigin(), videoCreateDTO.getClassification(),
        videoCreateDTO.getLargeArea(), videoCreateDTO.getGroupArea(), videoCreateDTO.getDetailArea(),
        videoCreateDTO.getParticleArea(), videoCreateDTO.getLevel(), videoCreateDTO.getDifficulty(),
        videoType.getType(), videoCreateDTO.getSource());
    ObjectMapper mapper = new ObjectMapper();
    // String viewDateStr = new
    // SimpleDateFormat("yyyy/MM/dd").format(videoCreateDTO.getViewDate());

    return mapper.writeValueAsString(CodeSet.builder().views(videoCreateDTO.getViews()).likes(videoCreateDTO.getLikes())
        .disLikes(videoCreateDTO.getDisLikes()).viewDate(commonUtils.dateToStr(videoCreateDTO.getViewDate()))
        .serialNum(videoCreateDTO.getSerialNum()).codes(codes).build());
  }

  public String makeType(VideoCreateDTO videoCreateDTO) {
    if (videoCreateDTO.getVideoType().equals(VideoType.ARTICLE.name()))
      return VideoType.ARTICLE.name();
    else if (videoCreateDTO.getVideoType().equals(VideoType.SELF.name())
        || videoCreateDTO.getVideoType().equals(VideoType.YOUTUBE.name()))
      return VideoType.VIDEO.name();
    else
      throw new ContentsException(ErrorCode.CODE_ERROR, "콘텐츠 종류 Code Error");
  }

  public VideoDTO convertVideoToDTO(Video video) {
    String source = getSource(video.getCodeSet());
    return convertVideoToDTO(video, source);
  }

  public VideoDTO convertVideoToDTO(Video video, String source) {
    String codeSetStr = video.getCodeSet();
    ObjectMapper mapper = new ObjectMapper();
    CodeSet codeSet = null;
    try {
      codeSet = mapper.readValue(codeSetStr, CodeSet.class);
    } catch (IOException e) {
      log.info(codeSetStr + ": Meta Code Json Error ");
      return null;
    }
    List<String> codes = codeSet.getCodes();
    Map<String, MetaCodeMaster> metaCodeMasterMap = metaCodeMasterRepositorySupport.findMetaCodeMasterMapByIds(codes);
    Map<String, CodeDTO> codeMap = metaCodeMasterMap.entrySet().stream()
        .collect(Collectors.toMap(e -> e.getKey(), e -> convertMetaToDTO(e.getValue())));
    Long ukVersionId = ukVersionManager.getCurrentUkVersionId();

    return VideoDTO.builder().videoId(video.getVideoId()).videoSrc(video.getVideoSrc()).title(video.getTitle())
        .imgSrc(video.getImgSrc()).subject(video.getVideoCurriculum().getSubject()).totalTime(video.getTotalTime())
        .startTime(video.getStartTime()).endTime(video.getEndTime()).hit(video.getVideoHit().getHit())
        .createDate(video.getCreateDate()).registerDate(video.getRegisterDate()).endDate(video.getEndDate())
        .videoType(video.getType().equals(VideoType.ARTICLE.name()) ? video.getType()
            : !commonUtils.stringNullCheck(video.getVideoSrc()) && video.getVideoSrc().contains(YOUTUBE_TYPE)
                ? VideoType.YOUTUBE.toString()
                : VideoType.SELF.toString())
        .description(video.getDescription())
        .uks(video.getVideoUks().stream()
            .map(videoUks -> videoUks.getUkMaster().getUkVersion().stream()
                .filter(ukVersion -> ukVersion.getVersionId().equals(ukVersionId)).findAny()
                .orElseThrow(() -> new ContentsException(ErrorCode.DB_ERROR, ukVersionId + ": UK Version not exist!"))
                .getUkName())
            .collect(Collectors.toList()))
        .hashtags(video.getVideoHashtags().stream().map(videoHashtags -> videoHashtags.getHashtag().getName())
            .collect(Collectors.toList()))
        // 상세 추가
        .ukIds(video.getVideoUks().stream().map(videoUks -> Long.valueOf(videoUks.getUkMaster().getUkId()))
            .collect(Collectors.toList()))
        .related(video.getRelated()).show(video.getShow()).classification(codeMap.get(codes.get(1)))
        .largeArea(codeMap.get(codes.get(2))).groupArea(codeMap.get(codes.get(3))).detailArea(codeMap.get(codes.get(4)))
        .particleArea(codeMap.get(codes.get(5))).level(codeMap.get(codes.get(6))).difficulty(codeMap.get(codes.get(7)))
        .source(codeMap.get(codes.get(9))).serialNum(codeSet.getSerialNum()).views(codeSet.getViews())
        .likes(codeSet.getLikes()).disLikes(codeSet.getDisLikes()).disLikes(codeSet.getDisLikes())
        .viewDate(commonUtils.strToDate(codeSet.getViewDate())).build();
  }

  public CodeDTO convertMetaToDTO(MetaCodeMaster metaCodeMaster) {
    return CodeDTO.builder().codeId(metaCodeMaster.getMetaCodeId()).domain(metaCodeMaster.getDomain())
        .name(metaCodeMaster.getCodeName()).code(metaCodeMaster.getCode()).build();
  }

  public ListDTO.Video convertVideoToDTO(List<Video> videos) {
    Map<String, String> sourceMap = getSources(
        videos.stream().map(video -> video.getCodeSet()).collect(Collectors.toList()));
    return new ListDTO.Video(videos.size(), videos.stream()
        .map(video -> this.convertVideoToDTO(video, sourceMap.get(video.getCodeSet()))).collect(Collectors.toList()));
  }

  public VideoDTO convertVideoJoinToDTO(VideoJoin videoJoin) {
    Video video = videoJoin.getVideo();
    String source = getSource(video.getCodeSet());
    return convertVideoToDTO(video, source);
  }

  public VideoDTO convertVideoJoinToDTO(VideoJoin videoJoin, String source) {
    Video video = videoJoin.getVideo();
    return convertVideoToDTO(video, source);
  }

  public ListDTO.Video convertVideoJoinToDTO(List<VideoJoin> videoJoins) {
    List<Video> videos = videoJoins.stream().map(videoJoin -> videoJoin.getVideo()).collect(Collectors.toList());
    return convertVideoToDTO(videos);
  }

  public ListDTO.Video convertVideoJoinToDTO(List<VideoJoin> videoJoins, Boolean recommended, Timestamp diagnosisDate,
      Integer riskScore, Integer investScore, Integer knowledgeScore) {
    ListDTO.Video videoDTO = convertVideoJoinToDTO(videoJoins);
    videoDTO.setRecommended(recommended);
    videoDTO.setRecommendDate(diagnosisDate.toLocalDateTime().toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
    videoDTO.setRiskScore(riskScore);
    videoDTO.setInvestScore(investScore);
    videoDTO.setKnowledgeScore(knowledgeScore);
    return videoDTO;
  }

  public ListDTO.Curriculum convertVideoCurriculumToDTO(List<VideoCurriculum> curriculums) {
    return new ListDTO.Curriculum(curriculums.size(),
        curriculums.stream()
            .map(curriculum -> new VideoCurriculumDTO(curriculum.getCurriculumId(), curriculum.getSubject()))
            .collect(Collectors.toList()));
  }

  private String getSourceId(String codeSetStr) {
    final String PREFIX_SOURCE_NAME = "콘텐츠 제작기관";

    if (commonUtils.stringNullCheck(codeSetStr)) {
      log.info("Meta Code Null Error");
      return null;
    }

    ObjectMapper mapper = new ObjectMapper();
    CodeSet codeSet = null;
    try {
      codeSet = mapper.readValue(codeSetStr, CodeSet.class);
    } catch (IOException e) {
      log.info(codeSetStr + ": Meta Code Json Error ");
      return null;
    }
    String sourceId = null;
    for (String codeId : codeSet.getCodes()) {
      if (codeId.contains(PREFIX_SOURCE_NAME)) {
        sourceId = codeId;
        break;
      }
    }
    return sourceId;
  }

  private String getSource(String codeSetStr) {
    String sourceId = getSourceId(codeSetStr);
    MetaCodeMaster metaCodeMaster = metaCodeMasterRepositorySupport.findMetaCodeById(sourceId);
    String sourceName = metaCodeMaster.getCodeName();

    return sourceName;
  }

  private Map<String, String> getSources(List<String> codeSetStrs) {
    HashMap<String, String> sourceMap = new HashMap<String, String>();
    ArrayList<String> sourceIds = new ArrayList<String>();
    for (String codeSetStr : codeSetStrs) {
      String sourceId = getSourceId(codeSetStr);
      sourceIds.add(sourceId);
    }

    Map<String, String> metaCodeMap = metaCodeMasterRepositorySupport.findMetaCodeMapByIds(sourceIds);
    for (int i = 0; i < codeSetStrs.size(); i++)
      sourceMap.put(codeSetStrs.get(i), metaCodeMap.get(sourceIds.get(i)));
    return sourceMap;
  }
}
