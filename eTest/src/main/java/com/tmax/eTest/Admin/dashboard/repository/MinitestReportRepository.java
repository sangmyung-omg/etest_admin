package com.tmax.eTest.Admin.dashboard.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tmax.eTest.Admin.dashboard.dto.FilterQueryDTO;
import com.tmax.eTest.Admin.dashboard.dto.MinitestDashboardDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.List;

import static com.tmax.eTest.Common.model.report.QMinitestReport.minitestReport;
import static com.tmax.eTest.Common.model.user.QUserMaster.userMaster;

@Repository
public class MinitestReportRepository extends UserFilterRepository {
    private final JPAQueryFactory query;

    public MinitestReportRepository (EntityManager entityManager) {
        this.query = new JPAQueryFactory(entityManager);
    }

    public List<MinitestDashboardDTO> filter(FilterQueryDTO filterQueryDTO) {
        return query.select(Projections.constructor(MinitestDashboardDTO.class,
                    minitestReport.minitestDate,
                    minitestReport.avgUkMastery,
                    minitestReport.minitestUkMastery))
                .from(minitestReport)
                .join(userMaster).on(userMaster.userUuid.eq(minitestReport.userUuid))
                .where(
                        investmentExperienceFilter(filterQueryDTO.getInvestmentExperience()),
                        dateFilter(filterQueryDTO.getDateFrom(), filterQueryDTO.getDateTo()),
                        ageGroupFilter(filterQueryDTO.getAgeGroupLowerBound(), filterQueryDTO.getAgeGroupUpperBound())
                )
                .orderBy(minitestReport.minitestDate.asc())
                .fetch();
    }

    private BooleanExpression dateFilter(Timestamp dateFrom, Timestamp dateTo){
        if (dateFrom == null & dateTo == null)
            return null;
        return minitestReport.minitestDate.between(dateFrom, dateTo);
    }
}
