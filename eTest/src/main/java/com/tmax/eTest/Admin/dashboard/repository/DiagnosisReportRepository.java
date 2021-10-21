package com.tmax.eTest.Admin.dashboard.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tmax.eTest.Admin.dashboard.dto.DiagnosisDashboardDTO;
import com.tmax.eTest.Admin.dashboard.dto.FilterRepoQueryDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.List;

import static com.tmax.eTest.Common.model.report.QDiagnosisReport.diagnosisReport;

@Repository("AD-DiagnosisReportRepository")
public class DiagnosisReportRepository extends UserFilterRepository {
    private final JPAQueryFactory query;

    public DiagnosisReportRepository(EntityManager entityManager) {
        this.query = new JPAQueryFactory(entityManager);
    }

    public List<DiagnosisDashboardDTO> filter(FilterRepoQueryDTO filterRepoQueryDTO){
        return query.select(Projections.constructor(DiagnosisDashboardDTO.class,
                    diagnosisReport.diagnosisDate,
                    diagnosisReport.userUuid,
                    diagnosisReport.giScore,
                    diagnosisReport.riskScore,
                    diagnosisReport.investScore,
                    diagnosisReport.knowledgeScore))
                .from(diagnosisReport)
                .where(
                        investmentExperienceFilter(filterRepoQueryDTO.getInvestmentExperience()),
                        dateFilter(filterRepoQueryDTO.getDateFrom(), filterRepoQueryDTO.getDateTo()),
                        ageGroupFilter(filterRepoQueryDTO.getAgeGroupLowerBound(), filterRepoQueryDTO.getAgeGroupUpperBound())
                )
                .orderBy(diagnosisReport.diagnosisDate.asc())
                .fetch();
    }

    public BooleanExpression investmentExperienceFilter(int investmentExperience) {
        if (investmentExperience == 0)
            return null;
        return diagnosisReport.investPeriod.eq(investmentExperience);
    }
    private BooleanExpression dateFilter(Timestamp dateFrom, Timestamp dateTo){
        if (dateFrom == null & dateTo == null)
            return null;
        return diagnosisReport.diagnosisDate.between(dateFrom, dateTo);
    }
}

