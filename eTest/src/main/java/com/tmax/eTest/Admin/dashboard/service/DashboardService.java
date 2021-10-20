package com.tmax.eTest.Admin.dashboard.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmax.eTest.Admin.dashboard.dto.*;
import com.tmax.eTest.Admin.dashboard.repository.*;
import com.tmax.eTest.Auth.repository.UserRepository;
import com.tmax.eTest.LRS.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
//@RequiredArgsConstructor
public class DashboardService {
    @Autowired
    @Qualifier("AU-UserRepository")
    private UserRepository userRepository;
    @Autowired
    @Qualifier(value ="AD-StatementRepository")
    private final StatementRepository statementRepository;
    @Autowired
    @Qualifier(value = "AD-DiagnosisReportRepository")
    private final DiagnosisReportRepository diagnosisReportRepository;
    private final MinitestReportRepository minitestReportRepository;
    private final UserCreateTimeRepository userCreateTimeRepository;
    private static final Logger logger = LoggerFactory.getLogger(DashboardService.class);
    private final ObjectMapper mapper;

    /** variables **/
    private List<DiagnosisDashboardDTO> diagnosisReports;
    private List<MinitestDashboardDTO> minitestReports;
    private List<StatementDashboardDTO> statements;
    private int diagnosisCollectBase;
    private int accessorCollectBase;

    private int hourLowerBound;
    private int accessorAtom;
    private int memberRegisteredAtom;
    private int memberWithdrawnAtom;
    private int diagnosisMemberAtom;
    private int diagnosisNotMemberAtom;
    private int minitestAtom;

    private String[] time;
    private int[] accessorCollect;
    private int[] memberTotal;
    private int[] memberRegistered;
    private int[] memberWithdrawn;

    private int[] diagnosisCollect;
    private int[] diagnosisAndMinitest;
    private int[] diagnosisTotal;
    private double[] diagnosisTotalRatio;
    private int[] diagnosisMember;
    private double[] diagnosisMemberRatio;
    private int[] diagnosisNotMember;
    private double[] diagnosisNotMemberRatio;
    private int[] minitest;
    private double[] minitestRatio;

    private int diagnosisCount;
    private String[] diagnosisScoreLegend = {"80점 이상", "60~79점", "41~60점", "40점 이하"};
    private double[] diagnosisScore;
    private int averageGI;
    private int averageRisk;
    private int averageInvest;
    private int averageKnowledge;

    private int minitestCount;
    private String[] minitestScoreLegend = {"80점 이상", "60~79점", "41~60점", "40점 이하"};
    private double[] minitestScore;
    private double averageMinitest;
    private Map<String, Double> minitestCategoryScoreSum;
    private Map<String, Integer> minitestCategoryCount;
    private List<String> minitestCategory;
    private List<Double> minitestCategoryAverage;

    private Timestamp timeLowerBound;
    private Timestamp timeUpperBound;
    private Timestamp diagnosisDate;
    private Calendar cal;
    /** variables **/

    public DashboardService(ObjectMapper mapper,
                            StatementRepository statementRepository,
                            DiagnosisReportRepository diagnosisReportRepository,
                            MinitestReportRepository minitestReportRepository,
                            UserCreateTimeRepository userCreateTimeRepository){
        this.mapper = mapper;
        this.statementRepository = statementRepository;
        this.diagnosisReportRepository = diagnosisReportRepository;
        this.minitestReportRepository = minitestReportRepository;
        this.userCreateTimeRepository = userCreateTimeRepository;
    }

    public FilterQueryDTO filterQueryBuilder(FilterDTO filterDTO) {
        FilterQueryDTO filterQueryDTO = FilterQueryDTO.builder()
                .gender(filterDTO.getGender())
                .investmentExperience(filterDTO.getInvestmentExperience())
                .build();
        if (filterDTO.getDateFrom() != null & filterDTO.getDateTo() != null){
            filterQueryDTO.setDateFrom(Timestamp.valueOf(filterDTO.getDateFrom()));
            filterQueryDTO.setDateTo(Timestamp.valueOf(filterDTO.getDateTo().plusDays(1)));
        }
        if (filterDTO.getAgeGroup() != null){
            LocalDate currentDateTime = LocalDate.now();
            filterQueryDTO.setAgeGroupLowerBound(currentDateTime.minusYears(filterDTO.getAgeGroup() + 10));
            filterQueryDTO.setAgeGroupUpperBound(currentDateTime.minusYears(filterDTO.getAgeGroup()));
        }
        return filterQueryDTO;
    }

    public List<DiagnosisDashboardDTO> getDiagnosis(FilterDTO filterDTO) {
        return diagnosisReportRepository.filter(filterQueryBuilder(filterDTO));
    }

    public List<MinitestDashboardDTO> getMinitest(FilterDTO filterDTO) {
        return minitestReportRepository.filter(filterQueryBuilder(filterDTO));
    }

    public List<StatementDashboardDTO> getStatements(FilterDTO filterDTO) {
        return statementRepository.filter(filterQueryBuilder(filterDTO), null);
    }

    public List<Statement> getUserRegister(FilterDTO filterDTO) { // 등록 회원 수
        return userCreateTimeRepository.filter(filterQueryBuilder(filterDTO));
    }

    public int getUserAll() {
        return userRepository.findAll().size();
    }

    private void initialize(int arraySize) {
        hourLowerBound = 0;
        time = new String[arraySize];
        accessorCollect = new int[arraySize];
        memberTotal = new int[arraySize];
        memberRegistered = new int[arraySize];
        memberWithdrawn = new int[arraySize];
        diagnosisCollect = new int[arraySize];
        diagnosisAndMinitest = new int[arraySize];
        diagnosisTotal = new int[arraySize];
        diagnosisTotalRatio = new double[arraySize];
        diagnosisMember = new int[arraySize];
        diagnosisMemberRatio = new double[arraySize];
        diagnosisNotMember = new int[arraySize];
        diagnosisNotMemberRatio = new double[arraySize];
        minitest = new int[arraySize];
        minitestRatio = new double[arraySize];
        diagnosisScore = new double[diagnosisScoreLegend.length];
        minitestScore = new double[minitestScoreLegend.length];
        minitestCategoryScoreSum = new HashMap<>();
        minitestCategoryCount = new HashMap<>();
        minitestCategory = new ArrayList<>();
        minitestCategoryAverage = new ArrayList<>();
        diagnosisCount = 0;
        averageGI = 0;
        averageRisk = 0;
        averageInvest = 0;
        averageKnowledge = 0;
        minitestCount = 0;
        averageMinitest = 0.0;
    }

    private void setTimeBound(LocalDateTime dateFrom, LocalDateTime dateTo) {
        if (dateFrom.equals(dateTo)) {
            cal = Calendar.getInstance();
            diagnosisDate = Timestamp.valueOf(dateFrom);
            cal.setTime(diagnosisDate);
            cal.set(Calendar.HOUR_OF_DAY, hourLowerBound);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            timeLowerBound = new Timestamp(cal.getTimeInMillis());
            cal.set(Calendar.HOUR_OF_DAY, hourLowerBound + 1);
            timeUpperBound = new Timestamp(cal.getTimeInMillis());
        }
        else {
            cal = Calendar.getInstance();
            Timestamp diagnosisDate = Timestamp.valueOf(dateFrom);
            cal.setTime(diagnosisDate);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            cal.set(Calendar.MILLISECOND, -1);
            timeLowerBound = new Timestamp(cal.getTimeInMillis());
            cal.set(Calendar.HOUR_OF_DAY, 47);
            timeUpperBound = new Timestamp(cal.getTimeInMillis());
        }
    }

    private int getaccessorCollectBase(FilterDTO filterDTO) {
        FilterQueryDTO filterQueryDTO = filterQueryBuilder(filterDTO);
        Timestamp bigbang = new Timestamp(Long.MIN_VALUE);
        Timestamp dateBound = filterQueryDTO.getDateFrom();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateBound);
        calendar.set(Calendar.MILLISECOND, -1);
        dateBound = new Timestamp(calendar.getTimeInMillis());
        filterQueryDTO.setDateFrom(bigbang);
        filterQueryDTO.setDateTo(dateBound);
        return statementRepository.filter(filterQueryDTO, "enter").size();
    }

    private int getDiagnosisCollectBase(FilterDTO filterDTO) {
        FilterQueryDTO filterQueryDTO = filterQueryBuilder(filterDTO);
        Timestamp bigbang = new Timestamp(Long.MIN_VALUE);
        Timestamp dateBound = filterQueryDTO.getDateFrom();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateBound);
        calendar.set(Calendar.MILLISECOND, -1);
        dateBound = new Timestamp(calendar.getTimeInMillis());
        filterQueryDTO.setDateFrom(bigbang);
        filterQueryDTO.setDateTo(dateBound);
        return diagnosisReportRepository.filter(filterQueryDTO).size()
                + minitestReportRepository.filter(filterQueryDTO).size();
    }

    private void checkMember() {
        if (statements.size() > 0) {
            for (StatementDashboardDTO statement : statements) {
                if (statement.getStatementDate().after(timeLowerBound)
                        & statement.getStatementDate().before(timeUpperBound)) {
                    if (statement.getActionType().equals("enter"))
                        accessorAtom++;
                    else if (statement.getActionType().equals("register"))
                        memberRegisteredAtom++;
                    else if (statement.getActionType().equals("withdrawl"))
                        memberWithdrawnAtom++;
                }
                else
                    break;
            }
        }
    }

    private void checkDiagnosisReport() {
        if (diagnosisReports.size() > 0) {
            for (DiagnosisDashboardDTO diagnosis : diagnosisReports) {
                diagnosisDate = diagnosis.getDiagnosisDate();
                if (diagnosisDate.after(timeLowerBound) & diagnosisDate.before(timeUpperBound)) {
                    if (diagnosis.getUserUuid().startsWith("NR-")) // 비회원
                        diagnosisNotMemberAtom++;
                    else
                        diagnosisMemberAtom++;

                    if (diagnosis.getGiScore() >= 80)
                        diagnosisScore[0]++;
                    else if (79 >= diagnosis.getGiScore() & diagnosis.getGiScore() >= 61)
                        diagnosisScore[1]++;
                    else if (60 >= diagnosis.getGiScore() & diagnosis.getGiScore() >= 41)
                        diagnosisScore[2]++;
                    else if (40 >= diagnosis.getGiScore())
                        diagnosisScore[3]++;
                    averageGI += diagnosis.getGiScore();

                    averageRisk += diagnosis.getRiskScore();
                    averageInvest += diagnosis.getInvestScore();
                    averageKnowledge += diagnosis.getKnowledgeScore();
                } else
                    break;
            }
        }
    }

    private void checkMinitestReports() {
        if(minitestReports.size() > 0) {
            for (MinitestDashboardDTO minitestReport : minitestReports) {
                diagnosisDate = minitestReport.getMinitestDate();
                if (diagnosisDate.after(timeLowerBound) & diagnosisDate.before(timeUpperBound))
                    minitestAtom++;
                else
                    break;

                if (minitestReport.getAvgUkMastery() >= 80)
                    minitestScore[0]++;
                else if (79 >= minitestReport.getAvgUkMastery() & minitestReport.getAvgUkMastery() >= 61)
                    minitestScore[1]++;
                else if (60 >= minitestReport.getAvgUkMastery() & minitestReport.getAvgUkMastery() >= 41)
                    minitestScore[2]++;
                else if (40 >= minitestReport.getAvgUkMastery())
                    minitestScore[3]++;
                averageMinitest += minitestReport.getAvgUkMastery();

                try {
                    JsonNode minitestUkMastery = mapper.readTree(minitestReport.getMinitestUkMastery());
                    for (Iterator<String> it = minitestUkMastery.fieldNames(); it.hasNext(); ) {
                        String minitestCategoryName = it.next();
                        JsonNode minitestUkInfo = minitestUkMastery.get(minitestCategoryName);
                        if(!minitestCategory.contains(minitestCategoryName)){
                            minitestCategory.add(minitestCategoryName);
                            minitestCategoryScoreSum.put(minitestCategoryName, 0.0);
                            minitestCategoryCount.put(minitestCategoryName, 0);
                        }
                        if (minitestUkInfo.isArray()) {
                            for (JsonNode entity : minitestUkInfo)
                                minitestCategoryScoreSum.put(minitestCategoryName,
                                        minitestCategoryScoreSum.get(minitestCategoryName) + entity.get(2).asDouble());
                            minitestCategoryCount.put(minitestCategoryName,
                                    minitestCategoryCount.get(minitestCategoryName) + minitestUkInfo.size());
                        }
                    }
                } catch (JsonProcessingException e) {
                    logger.error("JsonProcessingException");
                }
            }
        }
    }

    private void calculateMemberInfo(int timeIndex, LocalDateTime dateFrom, LocalDateTime dateTo) {
        int index;
        if (dateFrom.equals(dateTo)) {
            index = hourLowerBound;
            time[index] = index + 1 + ":00";
        } else {
            index = timeIndex;
            time[index] = (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
        }
        accessorCollectBase += accessorAtom;
        accessorCollect[index] = accessorCollectBase;
        memberRegistered[index] = memberRegisteredAtom;
        memberWithdrawn[index] = memberWithdrawnAtom;

        if (dateFrom.equals(dateTo)) {
            hourLowerBound++;
            cal.set(Calendar.HOUR_OF_DAY, hourLowerBound);
            timeLowerBound = new Timestamp(cal.getTimeInMillis());
            cal.set(Calendar.HOUR_OF_DAY, hourLowerBound + 1);
        }
        else {
            timeLowerBound = new Timestamp(cal.getTimeInMillis());
            cal.set(Calendar.HOUR_OF_DAY, 47);
        }
        timeUpperBound = new Timestamp(cal.getTimeInMillis());

        statements = statements.subList(accessorAtom + memberRegisteredAtom + memberWithdrawnAtom, statements.size());
        accessorAtom = 0;
        memberRegisteredAtom = 0;
        memberWithdrawnAtom = 0;
    }

    private void calculateDiagnosisInfo(int timeIndex, LocalDateTime dateFrom, LocalDateTime dateTo) {
        int index;
        if (dateFrom.equals(dateTo)) {
            index = hourLowerBound;
            time[index] = index + 1 + ":00";
        }else{
            index = timeIndex;
            time[index] = (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
        }
        diagnosisAndMinitest[index] = diagnosisMemberAtom + diagnosisNotMemberAtom + minitestAtom;
        diagnosisTotal[index] = diagnosisMemberAtom + diagnosisNotMemberAtom;
        diagnosisTotalRatio[index] = (double) (diagnosisMemberAtom + diagnosisNotMemberAtom)
                                    / (double) (diagnosisMemberAtom + diagnosisNotMemberAtom + minitestAtom);
        if (Double.isNaN(diagnosisTotalRatio[index]))
            diagnosisTotalRatio[index] = 0;
        diagnosisMember[index] = diagnosisMemberAtom;
        diagnosisMemberRatio[index] = (double) diagnosisMemberAtom
                / (double) (diagnosisMemberAtom + diagnosisNotMemberAtom);
        if (Double.isNaN(diagnosisMemberRatio[index]))
            diagnosisMemberRatio[index] = 0;
        diagnosisNotMember[index] = diagnosisNotMemberAtom;
        diagnosisNotMemberRatio[index] = (double) diagnosisNotMemberAtom
                / (double) (diagnosisMemberAtom + diagnosisNotMemberAtom);
        if (Double.isNaN(diagnosisNotMemberRatio[index]))
            diagnosisNotMemberRatio[index] = 0;
        minitest[index] = minitestAtom;
        minitestRatio[index] = (double) minitestAtom
                / (double) (diagnosisMemberAtom + diagnosisNotMemberAtom + minitestAtom);
        if (Double.isNaN(minitestRatio[index]))
            minitestRatio[index] = 0;
        diagnosisCollectBase += diagnosisAndMinitest[index];
        diagnosisCollect[index] = diagnosisCollectBase;
        diagnosisCount += diagnosisMemberAtom + diagnosisNotMemberAtom;
        minitestCount += minitestAtom;

        if (dateFrom.equals(dateTo)) {
            hourLowerBound++;
            cal.set(Calendar.HOUR_OF_DAY, hourLowerBound);
            timeLowerBound = new Timestamp(cal.getTimeInMillis());
            cal.set(Calendar.HOUR_OF_DAY, hourLowerBound + 1);
        }
        else {
            timeLowerBound = new Timestamp(cal.getTimeInMillis());
            cal.set(Calendar.HOUR_OF_DAY, 47);
        }
        timeUpperBound = new Timestamp(cal.getTimeInMillis());

        diagnosisReports = diagnosisReports.subList(diagnosisMemberAtom + diagnosisNotMemberAtom, diagnosisReports.size());
        minitestReports = minitestReports.subList(minitestAtom, minitestReports.size());
        diagnosisMemberAtom = 0;
        diagnosisNotMemberAtom = 0;
        minitestAtom = 0;
    }

    private void calculateMemberStatus() {
        memberTotal[memberTotal.length - 1] = getUserAll();
        for (int i = 1; i < memberTotal.length; i++)
            memberTotal[memberTotal.length - 1 - i]
                    = memberTotal[memberTotal.length - i]
                    - memberRegistered[memberTotal.length - i]
                    + memberWithdrawn[memberTotal.length - i];
    }

    private void calculateDiagnosisStatus() {
        for (int ds = 0; ds < diagnosisScore.length; ds++)
            diagnosisScore[ds] /= diagnosisCount;

        if (diagnosisCount != 0) {
            averageGI /= diagnosisCount;
            averageRisk /= diagnosisCount;
            averageInvest /= diagnosisCount;
            averageKnowledge /= diagnosisCount;
        }

        for (int ms = 0; ms < minitestScore.length; ms++)
            minitestScore[ms] /= minitestCount;
        if (minitestCount != 0)
            averageMinitest /= minitestCount;

        minitestCategory = new ArrayList<>(minitestCategoryScoreSum.keySet());
        for (String s : minitestCategory)
            minitestCategoryAverage.add(minitestCategoryScoreSum.get(s) / minitestCategoryCount.get(s));
        diagnosisCollectBase = 0;
    }

    public MemberStatusDTO getMemberStatus(FilterDTO filterDTO) {
        statements = getStatements(filterDTO);
        LocalDateTime dateFrom = filterDTO.getDateFrom();
        LocalDateTime dateTo = filterDTO.getDateTo();
        accessorCollectBase = getaccessorCollectBase(filterDTO);

        if (dateFrom.equals(dateTo)) { // 필터: 오늘
            initialize(23);
            setTimeBound(dateFrom, dateTo);
            while (hourLowerBound < 23){
                checkMember();
                calculateMemberInfo(0, dateFrom, dateTo);
            }
        }
        else { // 필터: 그 외
            int daysBetweenTwoDates = (int) Duration.between(dateFrom, dateTo).toDays() + 1;
            initialize(daysBetweenTwoDates);
            setTimeBound(dateFrom, dateTo);
            for (int timeIndex = 0; timeIndex < daysBetweenTwoDates; timeIndex++) {
                checkMember();
                calculateMemberInfo(timeIndex, dateFrom, dateTo);
            }
        }
        calculateMemberStatus();

        return MemberStatusDTO.builder()
                .time(time)
                .accessorCollect(accessorCollect)
                .memberTotal(memberTotal)
                .memberRegistered(memberRegistered)
                .memberWithdrawn(memberWithdrawn)
                .build();
    }

    public DiagnosisStatusDTO getDiagnosisStatus(FilterDTO filterDTO) {
        diagnosisReports = getDiagnosis(filterDTO);
        minitestReports = getMinitest(filterDTO);
        LocalDateTime dateFrom = filterDTO.getDateFrom();
        LocalDateTime dateTo = filterDTO.getDateTo();
        diagnosisCollectBase = getDiagnosisCollectBase(filterDTO);

        if (dateFrom.equals(dateTo)) { // 필터: 오늘
            initialize(23);
            setTimeBound(dateFrom, dateTo);
            while (hourLowerBound < 23){
                checkDiagnosisReport();
                checkMinitestReports();
                calculateDiagnosisInfo(0, dateFrom, dateTo);
            }
        }
        else { // 필터: 그 외
            int daysBetweenTwoDates = (int) Duration.between(dateFrom, dateTo).toDays() + 1;
            initialize(daysBetweenTwoDates);
            setTimeBound(dateFrom, dateTo);
            for (int timeIndex = 0; timeIndex < daysBetweenTwoDates; timeIndex++) {
                checkDiagnosisReport();
                checkMinitestReports();
                calculateDiagnosisInfo(timeIndex, dateFrom, dateTo);
            }
        }
        calculateDiagnosisStatus();

        return DiagnosisStatusDTO.builder()
                .time(time)
                .diagnosisCollect(diagnosisCollect)
                .diagnosisAndMinitest(diagnosisAndMinitest)
                .diagnosisTotal(diagnosisTotal)
                .diagnosisTotalRatio(diagnosisTotalRatio)
                .diagnosisMember(diagnosisMember)
                .diagnosisMemberRatio(diagnosisMemberRatio)
                .diagnosisNotMember(diagnosisNotMember)
                .diagnosisNotMemberRatio(diagnosisNotMemberRatio)
                .minitest(minitest)
                .minitestRatio(minitestRatio)
                .diagnosisCount(diagnosisCount)
                .diagnosisScoreLegend(diagnosisScoreLegend)
                .diagnosisScore(diagnosisScore)
                .averageGI(averageGI)
                .averageRisk(averageRisk)
                .averageInvest(averageInvest)
                .averageKnowledge(averageKnowledge)
                .minitestCount(minitestCount)
                .minitestScoreLegend(minitestScoreLegend)
                .minitestScore(minitestScore)
                .averageMinitest(averageMinitest)
                .minitestCategory(minitestCategory)
                .minitestCategoryAverage(minitestCategoryAverage)
                .build();
    }
}



