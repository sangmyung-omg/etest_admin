package com.tmax.eTest.Admin.dashboard.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tmax.eTest.Admin.dashboard.dto.DiagnosisDashboardDTO;
import com.tmax.eTest.Admin.dashboard.dto.FilterQueryDTO;
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

    public List<DiagnosisDashboardDTO> filter(FilterQueryDTO filterQueryDTO){
        return query.select(Projections.constructor(DiagnosisDashboardDTO.class,
                    diagnosisReport.diagnosisDate,
                    diagnosisReport.userUuid,
                    diagnosisReport.giScore,
                    diagnosisReport.riskScore,
                    diagnosisReport.investScore,
                    diagnosisReport.knowledgeScore))
                .from(diagnosisReport)
                .where(
                        investmentExperienceFilter(filterQueryDTO.getInvestmentExperience()),
                        dateFilter(filterQueryDTO.getDateFrom(), filterQueryDTO.getDateTo()),
                        ageGroupFilter(filterQueryDTO.getAgeGroupLowerBound(), filterQueryDTO.getAgeGroupUpperBound())
                )
                .orderBy(diagnosisReport.diagnosisDate.asc())
                .fetch();
    }

    // user가 아니라 Diagnosis의 invest period로 filter
    public BooleanExpression investmentExperienceFilter(int investmentExperience) {
        if (investmentExperience == 0) {
            return null;
        }
        return diagnosisReport.investPeriod.eq(investmentExperience);
    }
    private BooleanExpression dateFilter(Timestamp dateFrom, Timestamp dateTo){
        if (dateFrom == null & dateTo == null){
            return null;
        }
        return diagnosisReport.diagnosisDate.between(dateFrom, dateTo);
    }
}

