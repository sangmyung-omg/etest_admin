package com.tmax.eTest.Contents.job;

import java.sql.Date;
import java.time.LocalDate;

import javax.sql.DataSource;

import com.tmax.eTest.Contents.repository.support.VideoRepositorySupport;
import com.tmax.eTest.Contents.util.CommonUtils;
import com.tmax.eTest.Push.dto.CategoryPushRequestDTO;
import com.tmax.eTest.Push.service.PushService;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableBatchProcessing
public class ContentsPushJobConfiguration extends DefaultBatchConfigurer {

  // For Spring batch job to apply Tibero
  ////////////////////////////////////////////////////////////////
  @Autowired
  private DataSource dataSource;

  @Override
  protected JobRepository createJobRepository() throws Exception {
    JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
    factory.setDataSource(dataSource);
    factory.setDatabaseType("ORACLE");
    factory.setTransactionManager(jpaTransactionManager());
    factory.afterPropertiesSet();
    return factory.getObject();
  }

  @Bean
  @Primary
  public JpaTransactionManager jpaTransactionManager() {
    final JpaTransactionManager tm = new JpaTransactionManager();
    tm.setDataSource(dataSource);
    return tm;
  }
  ////////////////////////////////////////////////////////////////

  @Autowired
  public JobBuilderFactory jobBuilderFactory;

  @Autowired
  public StepBuilderFactory stepBuilderFactory;

  @Autowired
  private CommonUtils commonUtils;

  @Autowired
  private PushService pushService;

  @Autowired
  private VideoRepositorySupport videoRepositorySupport;

  @Bean
  public Job statJob() {
    return jobBuilderFactory.get("statJob").incrementer(new RunIdIncrementer()).start(statStep()).build();
  }

  @Bean
  public Step statStep() {
    return stepBuilderFactory.get("statStep").transactionManager(jpaTransactionManager())
        .tasklet((contribution, chunkContext) -> {

          LocalDate now = LocalDate.parse(chunkContext.getStepContext().getJobParameters().get("now").toString());
          Date nowDate = Date.valueOf(now);
          Date lastWeekDate = Date.valueOf(now.minusWeeks(1));
          log.info("Job Date: " + nowDate);

          Long update = videoRepositorySupport.findUpdateVideoSize(lastWeekDate, nowDate);

          String category = "content";
          String title = "금융투자 콘텐츠몰";
          String body = String.format("신규 콘텐츠 %d건이 업데이트 되었습니다.", update);

          pushService
              .categoryPushRequest(CategoryPushRequestDTO.builder().category(category).title(title).body(body).build())
              .block();

          log.info("Job Success!");
          return RepeatStatus.FINISHED;
        }).build();
  }
}
