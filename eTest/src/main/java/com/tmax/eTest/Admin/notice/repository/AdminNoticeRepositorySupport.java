package com.tmax.eTest.Admin.notice.repository;

import static com.tmax.eTest.Common.model.support.QNotice.notice;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tmax.eTest.Common.model.support.Notice;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class AdminNoticeRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory query;

    public AdminNoticeRepositorySupport(EntityManager entityManager) {
        super(Notice.class);
        this.query = new JPAQueryFactory(entityManager);
    }

    private BooleanExpression searchFilter(String search) {
        if (search == null)
            return null;
        return notice.title.contains(search);
    }

    public List<Notice> noticeList(String search) {
        return query.select(notice)
                .from(notice)
                .where(searchFilter(search))
                .fetch();
    }
}
