package com.tmax.eTest.Admin.dashboard.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tmax.eTest.Admin.dashboard.dto.FilterQueryDTO;
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

    public StatementRepository(EntityManager entityManager) {
        this.query = new JPAQueryFactory(entityManager);
    }

    public List<StatementDashboardDTO> filter(FilterQueryDTO filterQueryDTO, String actionType) {
        return query.select(Projections.constructor(StatementDashboardDTO.class,
                    statement.statementDate,
                    statement.actionType))
                .from(statement)
                .innerJoin(userMaster).on(statement.userId.eq(userMaster.userUuid))
                .where(
                        investmentExperienceFilter(filterQueryDTO.getInvestmentExperience()),
                        dateFilter(filterQueryDTO.getDateFrom(), filterQueryDTO.getDateTo()),
                        ageGroupFilter(filterQueryDTO.getAgeGroupLowerBound(), filterQueryDTO.getAgeGroupUpperBound()),
                        statement.sourceType.eq("application"),
                        actionTypeFilter(actionType)
                )
                .orderBy(statement.statementDate.asc())
                .fetch();
    }

    private BooleanExpression dateFilter(Timestamp dateFrom, Timestamp dateTo){
        if (dateFrom == null & dateTo == null)
            return null;
        return statement.statementDate.between(dateFrom, dateTo);
    }
    private BooleanExpression actionTypeFilter(String actionType){
        if (actionType == null)
            return null;
        return statement.actionType.eq(actionType);
    }
}


