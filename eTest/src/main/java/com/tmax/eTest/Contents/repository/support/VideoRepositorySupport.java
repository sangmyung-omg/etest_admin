package com.tmax.eTest.Contents.repository.support;

import static com.tmax.eTest.Common.model.video.QVideo.video;
import static com.tmax.eTest.Common.model.video.QVideoBookmark.videoBookmark;
import static com.tmax.eTest.Common.model.video.QVideoHashtag.videoHashtag;
import static com.tmax.eTest.Common.model.video.QVideoUkRel.videoUkRel;

import java.util.List;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tmax.eTest.Common.model.video.Video;
import com.tmax.eTest.Contents.dto.SortType;
import com.tmax.eTest.Contents.exception.ContentsException;
import com.tmax.eTest.Contents.exception.ErrorCode;
import com.tmax.eTest.Contents.util.CommonUtils;
import com.tmax.eTest.Contents.util.QuerydslUtils;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class VideoRepositorySupport extends QuerydslRepositorySupport {
  private final JPAQueryFactory query;

  public VideoRepositorySupport(JPAQueryFactory query) {
    super(Video.class);
    this.query = query;
  }

  public OrderSpecifier<?> getVideoSortedColumn(SortType sort) {
    switch (sort.toString()) {
      case "DATE":
        return QuerydslUtils.getSortedColumn(Order.ASC, video, "createDate");
      case "HIT":
        return QuerydslUtils.getSortedColumn(Order.ASC, video.videoHit, "hit");
      default:
        throw new ContentsException(ErrorCode.TYPE_ERROR, "Sort should be 'date' or 'hit' !!!");
    }
  }

  public List<Video> findVideosByUserAndCurriculum(String userId, Long curriculumId, SortType sort, String keyword) {
    return query.selectFrom(video).leftJoin(video.videoBookmarks, videoBookmark).on(userEq(userId))
        .where(curriculumEq(curriculumId)).where(checkKeyword(keyword)).orderBy(getVideoSortedColumn(sort)).fetch();
  }

  public List<Video> findBookmarkVideosByUserAndCurriculum(String userId, Long curriculumId, SortType sort,
      String keyword) {
    return query.selectFrom(video).join(video.videoBookmarks, videoBookmark)
        .where(userEq(userId), curriculumEq(curriculumId)).where(checkKeyword(keyword))
        .orderBy(getVideoSortedColumn(sort)).fetch();
  }

  private BooleanExpression userEq(String userId) {
    return CommonUtils.stringNullCheck(userId) ? null : videoBookmark.userUuid.eq(userId);
  }

  private BooleanExpression curriculumEq(Long curriculumId) {
    return CommonUtils.objectNullcheck(curriculumId) ? null : video.curriculumId.eq(curriculumId);
  }

  private BooleanExpression checkKeyword(String keyword) {
    return CommonUtils.stringNullCheck(keyword) ? null
        : video.title.contains(keyword)
            .or(video.videoUks.any()
                .in(JPAExpressions.selectFrom(videoUkRel).where(videoUkRel.video.eq(video),
                    videoUkRel.ukMaster.ukName.contains(keyword))))
            .or(video.videoHashtags.any().in(JPAExpressions.selectFrom(videoHashtag).where(videoHashtag.video.eq(video),
                videoHashtag.hashtag.name.contains(keyword))));
  }

}
