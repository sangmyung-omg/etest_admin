package com.tmax.eTest.Dashboard.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tmax.eTest.Dashboard.dto.DiagnosisDashboardDTO;
import com.tmax.eTest.Dashboard.dto.FilterRepoQueryDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.List;

import static com.tmax.eTest.Common.model.report.QDiagnosisReport.diagnosisReport;

@Repository("AD-DiagnosisReportRepository")
public class DiagnosisReportRepository {
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
                .where(dateFilter(filterRepoQueryDTO.getDateFrom(), filterRepoQueryDTO.getDateTo()))
                .orderBy(diagnosisReport.diagnosisDate.asc())
                .fetch();
    }

    private BooleanExpression dateFilter(Timestamp dateFrom, Timestamp dateTo){
        if (dateFrom == null & dateTo == null)
            return null;
        return diagnosisReport.diagnosisDate.between(dateFrom, dateTo);
    }
}

