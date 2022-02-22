package com.tmax.eTest.Contents.job;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

@Slf4j
@Component
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT1M", defaultLockAtLeastFor = "PT1M")
public class ContentsPushJobScheduler {

  @Autowired
  private JobLauncher jobLauncher;

  @Autowired
  private ContentsPushJobConfiguration contentsPushJobConfiguration;

  // @Scheduled(cron = "0 0 13 ? * FRI")
  @Scheduled(cron = "0 0 14 ? * TUE")
  @SchedulerLock(name = "ContentsPushJob")
  public void runJob() {

    Map<String, JobParameter> confMap = new HashMap<String, JobParameter>();
    confMap.put("now", new JobParameter(LocalDate.now().toString()));
    confMap.put("time", new JobParameter(System.currentTimeMillis()));
    JobParameters jobParameters = new JobParameters(confMap);
    try {
      jobLauncher.run(contentsPushJobConfiguration.pushJob(), jobParameters);
    } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException | JobParametersInvalidException
        | org.springframework.batch.core.repository.JobRestartException e) {
      log.error(e.getMessage());
    }
  }
}
