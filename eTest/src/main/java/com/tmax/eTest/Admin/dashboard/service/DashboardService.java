package com.tmax.eTest.Admin.dashboard.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmax.eTest.Admin.dashboard.dto.*;
import com.tmax.eTest.Admin.dashboard.repository.DiagnosisReportRepository;
import com.tmax.eTest.Admin.dashboard.repository.MinitestReportRepository;
import com.tmax.eTest.Admin.dashboard.repository.StatementRepository;
import com.tmax.eTest.Auth.repository.UserRepository;
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
public class DashboardService {
    @Autowired
    @Qualifier("AU-UserRepository")
    private final UserRepository userRepository;
    @Autowired
    @Qualifier(value ="AD-StatementRepository")
    private final StatementRepository statementRepository;
    @Autowired
    @Qualifier(value = "AD-DiagnosisReportRepository")
    private final DiagnosisReportRepository diagnosisReportRepository;
    private final MinitestReportRepository minitestReportRepository;
    private static final Logger logger = LoggerFactory.getLogger(DashboardService.class);
    private final ObjectMapper mapper;

    /** variables **/
    private List<DiagnosisDashboardDTO> diagnosisReports;
    private List<MinitestDashboardDTO> minitestReports;
    private List<StatementDashboardDTO> statements;
//    private int diagnosisCollectBase;
    private int accessorCollectBase;

    private int hourLowerBound;
    private int accessorAtom;
    private int memberRegisteredAtom;
    private int memberWithdrawnAtom;
    private int diagnosisMemberAtom;
    private int diagnosisNotMemberAtom;
    private int minitestAtom;
    private int viewsVideoAtom;
    private int viewsArticleAtom;
    private int viewsTextbookAtom;
//    private int viewsWikiAtom;

    private String[] time;
    private int[] accessorCollect;
    private int[] memberTotal;
    private int[] memberRegistered;
    private int[] memberWithdrawn;

//    private int[] diagnosisCollect;
    private int[] diagnosisAndMinitest;
    private int[] diagnosisTotal;
    private double[] diagnosisTotalRatio;
    private int[] diagnosisMember;
    private double[] diagnosisMemberRatio;
    private int[] diagnosisNotMember;
    private double[] diagnosisNotMemberRatio;
    private int[] minitest;
    private double[] minitestRatio;

    private int[] viewsTotal;
    private int[] viewsVideo;
    private double[] viewsVideoRatio;
    private int[] viewsArticle;
    private double[] viewsArticleRatio;
    private int[] viewsTextbook;
    private double[] viewsTextbookRatio;
//    private int[] viewsWiki;
//    private double[] viewsWikiRatio;

    private int diagnosisCount;
    private final String[] giScoreLegend = {"80점 이상", "60~79점", "41~60점", "40점 이하"};
    private double[] giScoreRatio;
    private double averageGI;
    private double averageRisk;
    private double averageInvest;
    private double averageKnowledge;

    private int minitestCount;
    private final String[] minitestScoreLegend = {"80점 이상", "60~79점", "41~60점", "40점 이하"};
    private double[] minitestScoreRatio;
    private double averageMinitest;
    private Map<String, Double> minitestCategoryScoreSum;
    private Map<String, Integer> minitestCategoryCount;
    private List<String> minitestCategoryName;
    private List<Double> minitestCategoryAverage;

    private Timestamp timeLowerBound;
    private Timestamp timeUpperBound;
    private Timestamp diagnosisDate;
    private Calendar cal;

    private List<DiagnosisInfoDTO> diagnosisInfo;
    private List<ContentViewsInfoDTO> contentViewsInfo;
    /** end of variables **/

    public DashboardService(ObjectMapper mapper,
                            UserRepository userRepository,
                            StatementRepository statementRepository,
                            DiagnosisReportRepository diagnosisReportRepository,
                            MinitestReportRepository minitestReportRepository){
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.statementRepository = statementRepository;
        this.diagnosisReportRepository = diagnosisReportRepository;
        this.minitestReportRepository = minitestReportRepository;
    }

    public FilterRepoQueryDTO filterRepoQueryBuilder(FilterDTO filterDTO) {
        FilterRepoQueryDTO filterRepoQueryDTO = FilterRepoQueryDTO.builder()
                .gender(filterDTO.getGender())
                .investmentExperience(filterDTO.getInvestmentExperience())
                .build();
        if (filterDTO.getDateFrom() != null & filterDTO.getDateTo() != null){
            filterRepoQueryDTO.setDateFrom(Timestamp.valueOf(filterDTO.getDateFrom()));
            filterRepoQueryDTO.setDateTo(Timestamp.valueOf(filterDTO.getDateTo().plusDays(1)));
        }
        if (filterDTO.getAgeGroup() != null){
            LocalDate currentDateTime = LocalDate.now();
            filterRepoQueryDTO.setAgeGroupLowerBound(currentDateTime.minusYears(filterDTO.getAgeGroup() + 10));
            filterRepoQueryDTO.setAgeGroupUpperBound(currentDateTime.minusYears(filterDTO.getAgeGroup()));
        }
        return filterRepoQueryDTO;
    }

    public List<DiagnosisDashboardDTO> getDiagnosis(FilterDTO filterDTO) {
        return diagnosisReportRepository.filter(filterRepoQueryBuilder(filterDTO));
    }

    public List<MinitestDashboardDTO> getMinitest(FilterDTO filterDTO) {
        return minitestReportRepository.filter(filterRepoQueryBuilder(filterDTO));
    }

    public List<StatementDashboardDTO> getStatements(FilterDTO filterDTO, String sourceType, String actionType) {
        return statementRepository.filter(filterRepoQueryBuilder(filterDTO), sourceType, actionType);
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
//        diagnosisCollect = new int[arraySize];
        diagnosisAndMinitest = new int[arraySize];
        diagnosisTotal = new int[arraySize];
        diagnosisTotalRatio = new double[arraySize];
        diagnosisMember = new int[arraySize];
        diagnosisMemberRatio = new double[arraySize];
        diagnosisNotMember = new int[arraySize];
        diagnosisNotMemberRatio = new double[arraySize];
        minitest = new int[arraySize];
        minitestRatio = new double[arraySize];
        viewsTotal = new int[arraySize];
        viewsVideo = new int[arraySize];
        viewsVideoRatio = new double[arraySize];
        viewsArticle = new int[arraySize];
        viewsArticleRatio = new double[arraySize];
        viewsTextbook = new int[arraySize];
        viewsTextbookRatio = new double[arraySize];
//        viewsWiki = new int[arraySize];
//        viewsWikiRatio = new double[arraySize];
        giScoreRatio = new double[giScoreLegend.length];
        minitestScoreRatio = new double[minitestScoreLegend.length];
        minitestCategoryScoreSum = new HashMap<>();
        minitestCategoryCount = new HashMap<>();
        minitestCategoryName = new ArrayList<>();
        minitestCategoryAverage = new ArrayList<>();
        diagnosisCount = 0;
        averageGI = 0.0;
        averageRisk = 0.0;
        averageInvest = 0.0;
        averageKnowledge = 0.0;
        minitestCount = 0;
        averageMinitest = 0;
        diagnosisInfo = new ArrayList<>();
        contentViewsInfo = new ArrayList<>();
    }

    private void initializeAtom() {
        accessorAtom = 0;
        memberRegisteredAtom = 0;
        memberWithdrawnAtom = 0;
        diagnosisMemberAtom = 0;
        diagnosisNotMemberAtom = 0;
        minitestAtom = 0;
        viewsVideoAtom = 0;
        viewsArticleAtom = 0;
        viewsTextbookAtom = 0;
//        viewsWikiAtom = 0;
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

    private int getAccessorCollectBase(FilterDTO filterDTO) {
        FilterRepoQueryDTO filterRepoQueryDTO = filterRepoQueryBuilder(filterDTO);
        Timestamp bigbang = new Timestamp(Long.MIN_VALUE);
        Timestamp dateBound = filterRepoQueryDTO.getDateFrom();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateBound);
        calendar.set(Calendar.MILLISECOND, -1);
        dateBound = new Timestamp(calendar.getTimeInMillis());
        filterRepoQueryDTO.setDateFrom(bigbang);
        filterRepoQueryDTO.setDateTo(dateBound);
        return statementRepository.filter(filterRepoQueryDTO, "application", "enter").size();
    }

//    private int getDiagnosisCollectBase(FilterDTO filterDTO) {
//        FilterRepoQueryDTO filterRepoQueryDTO = filterRepoQueryBuilder(filterDTO);
//        Timestamp bigbang = new Timestamp(Long.MIN_VALUE);
//        Timestamp dateBound = filterRepoQueryDTO.getDateFrom();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(dateBound);
//        calendar.set(Calendar.MILLISECOND, -1);
//        dateBound = new Timestamp(calendar.getTimeInMillis());
//        filterRepoQueryDTO.setDateFrom(bigbang);
//        filterRepoQueryDTO.setDateTo(dateBound);
//        return diagnosisReportRepository.filter(filterRepoQueryDTO).size()
//                + minitestReportRepository.filter(filterRepoQueryDTO).size();
//    }

    private void checkMember() {
        if (statements.size() > 0) {
            for (StatementDashboardDTO statement : statements) {
                if (statement.getStatementDate().after(timeLowerBound)
                        & statement.getStatementDate().before(timeUpperBound)) {
                    switch (statement.getActionType()) {
                        case "enter":
                            accessorAtom++;
                            break;
                        case "register":
                            memberRegisteredAtom++;
                            break;
                        case "withdrawal":
                            memberWithdrawnAtom++;
                            break;
                    }
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
                    if (diagnosis.getUserUuid().startsWith("NR-"))
                        diagnosisNotMemberAtom++;
                    else
                        diagnosisMemberAtom++;

                    if (diagnosis.getGiScore() >= 80)
                        giScoreRatio[0]++;
                    else if (79 >= diagnosis.getGiScore() & diagnosis.getGiScore() >= 61)
                        giScoreRatio[1]++;
                    else if (60 >= diagnosis.getGiScore() & diagnosis.getGiScore() >= 41)
                        giScoreRatio[2]++;
                    else if (40 >= diagnosis.getGiScore())
                        giScoreRatio[3]++;
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
                    minitestScoreRatio[0]++;
                else if (79 >= minitestReport.getAvgUkMastery() & minitestReport.getAvgUkMastery() >= 61)
                    minitestScoreRatio[1]++;
                else if (60 >= minitestReport.getAvgUkMastery() & minitestReport.getAvgUkMastery() >= 41)
                    minitestScoreRatio[2]++;
                else if (40 >= minitestReport.getAvgUkMastery())
                    minitestScoreRatio[3]++;
                averageMinitest += minitestReport.getAvgUkMastery();

                try {
                    JsonNode minitestUkMastery = mapper.readTree(minitestReport.getMinitestUkMastery());
                    for (Iterator<String> it = minitestUkMastery.fieldNames(); it.hasNext(); ) {
                        String minitestCategory = it.next();
                        JsonNode minitestUkInfo = minitestUkMastery.get(minitestCategory);
                        if(!minitestCategoryName.contains(minitestCategory)){
                            minitestCategoryName.add(minitestCategory);
                            minitestCategoryScoreSum.put(minitestCategory, 0.0);
                            minitestCategoryCount.put(minitestCategory, 0);
                        }
                        if (minitestUkInfo.isArray()) {
                            for (JsonNode entity : minitestUkInfo)
                                minitestCategoryScoreSum.put(minitestCategory,
                                        minitestCategoryScoreSum.get(minitestCategory) + entity.get(2).asDouble());
                            minitestCategoryCount.put(minitestCategory,
                                    minitestCategoryCount.get(minitestCategory) + minitestUkInfo.size());
                        }
                    }
                } catch (JsonProcessingException e) {
                    logger.error("JsonProcessingException");
                }
            }
        }
    }

    private void checkContentViews() {
        if (statements.size() > 0) {
            for (StatementDashboardDTO statement : statements) {
                if (statement.getStatementDate().after(timeLowerBound)
                        & statement.getStatementDate().before(timeUpperBound)) {
                    switch (statement.getSourceType()) {
                        case "video":
                            viewsVideoAtom++;
                            break;
                        case "article":
                            viewsArticleAtom++;
                            break;
                        case "textbook":
                            viewsTextbookAtom++;
                            break;
//                        case "wiki":
//                            viewsWikiAtom++;
//                            break;
                    }
                }
                else
                    break;
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
        initializeAtom();
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
//        diagnosisCollectBase += diagnosisAndMinitest[index];
//        diagnosisCollect[index] = diagnosisCollectBase;
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
        initializeAtom();
    }

    private void calculateContentViewsInfo(int timeIndex, LocalDateTime dateFrom, LocalDateTime dateTo) {
        int index;
        if (dateFrom.equals(dateTo)) {
            index = hourLowerBound;
            time[index] = index + 1 + ":00";
        } else {
            index = timeIndex;
            time[index] = (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
        }
//        viewsTotal[index] = viewsVideoAtom + viewsArticleAtom + viewsTextbookAtom + viewsWikiAtom;
        viewsTotal[index] = viewsVideoAtom + viewsArticleAtom + viewsTextbookAtom;
        viewsVideo[index] = viewsVideoAtom;
        viewsVideoRatio[index] = (double) viewsVideoAtom / (double) viewsTotal[index];
        if (Double.isNaN(viewsVideoRatio[index]))
            viewsVideoRatio[index] = 0;
        viewsArticle[index] = viewsArticleAtom;
        viewsArticleRatio[index] = (double) viewsArticleAtom / (double) viewsTotal[index];
        if (Double.isNaN(viewsArticleRatio[index]))
            viewsArticleRatio[index] = 0;
        viewsTextbook[index] = viewsTextbookAtom;
        viewsTextbookRatio[index] = (double) viewsTextbookAtom / (double) viewsTotal[index];
        if (Double.isNaN(viewsTextbookRatio[index]))
            viewsTextbookRatio[index] = 0;
//        viewsWiki[index] = viewsWikiAtom;
//        viewsWikiRatio[index] = (double) viewsWikiAtom / (double) viewsTotal[index];
//        if (Double.isNaN(viewsWikiRatio[index]))
//            viewsWikiRatio[index] = 0;

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

//        statements = statements.subList(viewsVideoAtom + viewsArticleAtom + viewsTextbookAtom + viewsWikiAtom, statements.size());
        statements = statements.subList(viewsVideoAtom + viewsArticleAtom + viewsTextbookAtom, statements.size());
        initializeAtom();
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
        for (int ds = 0; ds < giScoreRatio.length; ds++)
            giScoreRatio[ds] = Double.isNaN(giScoreRatio[ds] / diagnosisCount) ? 0.0 : giScoreRatio[ds] / diagnosisCount;

        if (diagnosisCount != 0) {
            averageGI /= diagnosisCount;
            averageRisk /= diagnosisCount;
            averageInvest /= diagnosisCount;
            averageKnowledge /= diagnosisCount;
        }

        for (int ms = 0; ms < minitestScoreRatio.length; ms++)
            minitestScoreRatio[ms] = Double.isNaN(minitestScoreRatio[ms] / minitestCount) ? 0.0 : minitestScoreRatio[ms] / minitestCount;

        if (minitestCount != 0)
            averageMinitest /= minitestCount;

        minitestCategoryName = new ArrayList<>(minitestCategoryScoreSum.keySet());
        for (String s : minitestCategoryName)
            minitestCategoryAverage.add(minitestCategoryScoreSum.get(s) / minitestCategoryCount.get(s));
//        diagnosisCollectBase = 0;

        for (int i = 0; i < time.length; i++)
            diagnosisInfo.add(DiagnosisInfoDTO.builder()
                    .time(time[i])
                    .diagnosisAndMinitest(diagnosisAndMinitest[i])
                    .diagnosisTotal(diagnosisTotal[i])
                    .diagnosisTotalRatio(diagnosisTotalRatio[i])
                    .diagnosisMember(diagnosisMember[i])
                    .diagnosisMemberRatio(diagnosisMemberRatio[i])
                    .diagnosisNotMember(diagnosisNotMember[i])
                    .diagnosisNotMemberRatio(diagnosisNotMemberRatio[i])
                    .build());
    }

    private void calculateContentViewsStatus() {
        for (int i = 0; i < time.length; i++)
            contentViewsInfo.add(ContentViewsInfoDTO.builder()
                    .time(time[i])
                    .viewsTotal(viewsTotal[i])
                    .viewsVideo(viewsVideo[i])
                    .viewsVideoRatio(viewsVideoRatio[i])
                    .viewsArticle(viewsArticle[i])
                    .viewsArticleRatio(viewsArticleRatio[i])
                    .viewsTextbook(viewsTextbook[i])
                    .viewsTextbookRatio(viewsTextbookRatio[i])
//                    .viewsWiki(viewsWiki[i])
//                    .viewsWikiRatio(viewsWikiRatio[i])
                    .build());
    }

    public OverallStatusDTO getOverallStatus(FilterDTO filterDTO) {
        double serviceUsageDivisor = 0;
        initializeAtom();
        diagnosisReports = getDiagnosis(filterDTO);
        minitestReports = getMinitest(filterDTO);
        serviceUsageDivisor += diagnosisReports.size() + minitestReports.size();
        statements = getStatements(filterDTO, "application", null);
        for (StatementDashboardDTO statement : statements) {
            switch (statement.getActionType()) {
                case "enter":
                    accessorAtom++;
                    break;
                case "register":
                    memberRegisteredAtom++;
                    break;
                case "withdrawal":
                    memberWithdrawnAtom++;
                    break;
            }
        }

        giScoreRatio = new double[giScoreLegend.length];
        for (DiagnosisDashboardDTO diagnosis : diagnosisReports) {
                if (diagnosis.getGiScore() >= 80)
                    giScoreRatio[0]++;
                else if (79 >= diagnosis.getGiScore() & diagnosis.getGiScore() >= 61)
                    giScoreRatio[1]++;
                else if (60 >= diagnosis.getGiScore() & diagnosis.getGiScore() >= 41)
                    giScoreRatio[2]++;
                else if (40 >= diagnosis.getGiScore())
                    giScoreRatio[3]++;
                averageGI += diagnosis.getGiScore();
        }

        minitestScoreRatio = new double[minitestScoreLegend.length];
        for (MinitestDashboardDTO minitestReport : minitestReports) {
            if (minitestReport.getAvgUkMastery() >= 80)
                minitestScoreRatio[0]++;
            else if (79 >= minitestReport.getAvgUkMastery() & minitestReport.getAvgUkMastery() >= 61)
                minitestScoreRatio[1]++;
            else if (60 >= minitestReport.getAvgUkMastery() & minitestReport.getAvgUkMastery() >= 41)
                minitestScoreRatio[2]++;
            else if (40 >= minitestReport.getAvgUkMastery())
                minitestScoreRatio[3]++;
            averageMinitest += minitestReport.getAvgUkMastery();
        }

        if (diagnosisCount != 0)
            averageGI /= diagnosisCount;
        if (minitestCount != 0)
            averageMinitest /= minitestCount;

        statements = getStatements(filterDTO, "content", "enter");
        for (StatementDashboardDTO statement : statements) {
            switch (statement.getSourceType()) {
                case "video":
                    viewsVideoAtom++;
                    break;
                case "article":
                    viewsArticleAtom++;
                    break;
                case "textbook":
                    viewsTextbookAtom++;
                    break;
//                case "wiki":
//                    viewsWikiAtom++;
//                    break;
            }
            serviceUsageDivisor++;
        }

        return OverallStatusDTO.builder()
                .accessorCollect(accessorAtom)
//                .memberCountOfChange(memberRegisteredAtom - memberWithdrawnAtom)
                .memberRegistered(memberRegisteredAtom)
//                .memberWithdrawn(memberWithdrawnAtom)
                .memberTotal(getUserAll())
                .diagnosisTotal(diagnosisReports.size() + minitestReports.size())
                .diagnosis(diagnosisReports.size())
                .minitest(minitestReports.size())
                .giScoreLegend(giScoreLegend)
                .giScoreRatio(giScoreRatio)
                .minitestScoreLegend(minitestScoreLegend)
                .minitestScoreRatio(minitestScoreRatio)
                .serviceUsageRatio(ServiceUsageDTO.builder()
                        .diagnosis(Double.isNaN(diagnosisReports.size() / serviceUsageDivisor) ?
                                0.0 : diagnosisReports.size() / serviceUsageDivisor)
                        .minitest(Double.isNaN(minitestReports.size() / serviceUsageDivisor) ?
                                0.0 : minitestReports.size() / serviceUsageDivisor)
                        .video(Double.isNaN(viewsVideoAtom / serviceUsageDivisor) ?
                                0.0 : viewsVideoAtom / serviceUsageDivisor)
                        .article(Double.isNaN(viewsArticleAtom / serviceUsageDivisor) ?
                                0.0 : viewsArticleAtom / serviceUsageDivisor)
                        .textbook(Double.isNaN(viewsTextbookAtom / serviceUsageDivisor) ?
                                0.0 : viewsTextbookAtom / serviceUsageDivisor)
                        .build())
                .build();
    }

    public MemberStatusDTO getMemberStatus(FilterDTO filterDTO) {
        statements = getStatements(filterDTO, "application", "member");
        LocalDateTime dateFrom = filterDTO.getDateFrom();
        LocalDateTime dateTo = filterDTO.getDateTo();
        accessorCollectBase = getAccessorCollectBase(filterDTO);

        if (dateFrom.equals(dateTo)) {
            initialize(23);
            setTimeBound(dateFrom, dateTo);
            while (hourLowerBound < 23){
                checkMember();
                calculateMemberInfo(0, dateFrom, dateTo);
            }
        }
        else {
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
                .accessor(CollectDTO.builder()
                        .time(time)
                        .collect(accessorCollect)
                        .build())
                .memberChange(MemberStatusMemberChangeDTO.builder()
                        .time(time)
                        .memberTotal(memberTotal)
                        .memberRegistered(memberRegistered)
//                        .memberWithdrawn(memberWithdrawn)
                        .build())
                .build();
    }

    public DiagnosisStatusDTO getDiagnosisStatus(FilterDTO filterDTO) {
        diagnosisReports = getDiagnosis(filterDTO);
        minitestReports = getMinitest(filterDTO);
        LocalDateTime dateFrom = filterDTO.getDateFrom();
        LocalDateTime dateTo = filterDTO.getDateTo();
//        diagnosisCollectBase = getDiagnosisCollectBase(filterDTO);

        if (dateFrom.equals(dateTo)) {
            initialize(23);
            setTimeBound(dateFrom, dateTo);
            while (hourLowerBound < 23){
                checkDiagnosisReport();
                checkMinitestReports();
                calculateDiagnosisInfo(0, dateFrom, dateTo);
            }
        }
        else {
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
//                .diagnosisCollect(CollectDTO.builder()
//                        .time(time)
//                        .collect(diagnosisCollect)
//                        .build())
                .diagnosisPerTime(DiagnosisPerTimeDTO.builder()
                        .time(time)
                        .diagnosisMember(diagnosisMember)
                        .diagnosisNotMember(diagnosisNotMember)
                        .minitest(minitest)
                        .build())
                .diagnosisInfo(diagnosisInfo)
                .diagnosisStatus(ReportStatusDTO.builder()
                        .diagnosisCount(diagnosisCount)
                        .scoreLegend(giScoreLegend)
                        .scoreRatio(giScoreRatio)
                        .averageScore(averageGI)
                        .categoryName(Arrays.asList("위험대응", "행동편향", "지식이해"))
                        .categoryAverageScore(Arrays.asList(averageRisk, averageInvest, averageKnowledge))
                        .build())
                .minitestStatus(ReportStatusDTO.builder()
                        .diagnosisCount(minitestCount)
                        .scoreLegend(minitestScoreLegend)
                        .scoreRatio(minitestScoreRatio)
                        .averageScore(averageMinitest)
                        .categoryName(minitestCategoryName)
                        .categoryAverageScore(minitestCategoryAverage)
                        .build())
                .build();
    }

    public ContentViewsStatusDTO getContentViewsStatus(FilterDTO filterDTO) {
        statements = getStatements(filterDTO, "content", "enter");
        LocalDateTime dateFrom = filterDTO.getDateFrom();
        LocalDateTime dateTo = filterDTO.getDateTo();

        if (dateFrom.equals(dateTo)) {
            initialize(23);
            setTimeBound(dateFrom, dateTo);
            while (hourLowerBound < 23){
                checkContentViews();
                calculateContentViewsInfo(0, dateFrom, dateTo);
            }
        }
        else {
            int daysBetweenTwoDates = (int) Duration.between(dateFrom, dateTo).toDays() + 1;
            initialize(daysBetweenTwoDates);
            setTimeBound(dateFrom, dateTo);
            for (int timeIndex = 0; timeIndex < daysBetweenTwoDates; timeIndex++) {
                checkContentViews();
                calculateContentViewsInfo(timeIndex, dateFrom, dateTo);
            }
        }
        calculateContentViewsStatus();
        return ContentViewsStatusDTO.builder()
                .contentViews(ContentViewsDTO.builder()
                        .time(time)
                        .viewsVideo(viewsVideo)
                        .viewsArticle(viewsArticle)
                        .viewsTextbook(viewsTextbook)
//                        .viewsWiki(viewsWiki)
                        .build())
                .contentViewsInfo(contentViewsInfo)
                .build();
    }
}
