package com.tmax.eTest.Admin.dashboard.repository;

import static com.tmax.eTest.Common.model.report.QDiagnosisReport.diagnosisReport;
import static com.tmax.eTest.Common.model.user.QUserMaster.userMaster;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import com.tmax.eTest.Admin.dashboard.dto.FilterQueryDTO;
import com.tmax.eTest.Common.model.report.DiagnosisReport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.List;

@Repository("AD-DiagnosisReportRepository")
public class DiagnosisReportRepository extends UserFilterRepository {
    private final JPAQueryFactory query;

    public DiagnosisReportRepository(EntityManager entityManager) {
        this.query = new JPAQueryFactory(entityManager);
    }

    public List<DiagnosisReport> filter(FilterQueryDTO filterQueryDTO){
        return query.select(diagnosisReport)
                .from(diagnosisReport).join(userMaster)
                .on(userMaster.userUuid.eq(diagnosisReport.userUuid))
                .where(
                        dateFilter(filterQueryDTO.getDateFrom(), filterQueryDTO.getDateTo()),
                        genderFilter(filterQueryDTO.getGender()),
                        ageGroupFilter(filterQueryDTO.getAgeGroupLowerBound(), filterQueryDTO.getAgeGroupUpperBound())
                )
                .fetch();
    }

    private BooleanExpression dateFilter(Timestamp dateFrom, Timestamp dateTo){
        if (dateFrom == null & dateTo == null)
            return null;
        return diagnosisReport.diagnosisDate.between(dateFrom, dateTo);
    }
}
