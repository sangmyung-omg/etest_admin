package com.tmax.eTest.Admin.dashboard.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tmax.eTest.Admin.dashboard.dto.FilterRepoQueryDTO;
import com.tmax.eTest.Admin.dashboard.dto.StatementDashboardDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.List;

import static com.tmax.eTest.Common.model.user.QUserMaster.userMaster;
import static com.tmax.eTest.LRS.model.QStatement.statement;

@Repository("AD-StatementRepository")
public class StatementRepository extends UserFilterRepository {
    private final JPAQueryFactory query;

    public StatementRepository(EntityManager entityManager) { this.query = new JPAQueryFactory(entityManager); }

    public List<StatementDashboardDTO> filter(FilterRepoQueryDTO filterRepoQueryDTO, String sourceType, String actionType) {
        return query.select(Projections.constructor(StatementDashboardDTO.class,
                    statement.statementDate,
                    statement.actionType,
                    statement.sourceType))
                .from(statement)
                .innerJoin(userMaster).on(statement.userId.eq(userMaster.userUuid))
                .where(
                        investmentExperienceFilter(filterRepoQueryDTO.getInvestmentExperience()),
                        dateFilter(filterRepoQueryDTO.getDateFrom(), filterRepoQueryDTO.getDateTo()),
                        ageGroupFilter(filterRepoQueryDTO.getAgeGroupLowerBound(), filterRepoQueryDTO.getAgeGroupUpperBound()),
                        actionTypeFilter(actionType),
                        sourceTypeFilter(sourceType)
                )
                .orderBy(statement.statementDate.asc())
                .fetch();
    }

    private BooleanExpression dateFilter(Timestamp dateFrom, Timestamp dateTo){
        if (dateFrom == null & dateTo == null)
            return null;
        return statement.statementDate.between(dateFrom, dateTo);
    }

    private BooleanExpression sourceTypeFilter(String sourceType){
        if (sourceType == null)
            return null;
        else if (sourceType.equals("content"))
            return statement.sourceType.eq("video").or(statement.sourceType.eq("article"))
                    .or(statement.sourceType.eq("textbook"));
//                    .or(statement.sourceType.eq("textbook")).or(statement.sourceType.eq("wiki"));
        return statement.sourceType.eq(sourceType);
    }

    private BooleanExpression actionTypeFilter(String actionType){
        if (actionType == null)
            return null;
        else if (actionType.equals("member"))
            return statement.actionType.eq("enter")
                    .or(statement.actionType.eq("withdrawal"))
                    .or(statement.actionType.eq("register"));
        return statement.actionType.eq(actionType);
    }
}


