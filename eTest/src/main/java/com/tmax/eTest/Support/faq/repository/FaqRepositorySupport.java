package com.tmax.eTest.Support.faq.repository;

import static com.tmax.eTest.Common.model.support.QFAQ.fAQ;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tmax.eTest.Common.model.support.FAQ;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class FaqRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory query;

    public FaqRepositorySupport(EntityManager entityManager) {
        super(FAQ.class);
        this.query = new JPAQueryFactory(entityManager);
    }

    private BooleanExpression searchFilter(String search) {
        if (search == null)
            return null;
        return fAQ.title.contains(search);
    }

    public List<FAQ> faqList(List<String> categories, String search){
        BooleanBuilder builder = new BooleanBuilder();
        if (categories != null)
            for (String category : categories)
                builder.or(fAQ.category.eq(category));
        return query.select(fAQ)
                .from(fAQ)
                .where(builder.and(searchFilter(search)))
                .fetch();
    }
}
