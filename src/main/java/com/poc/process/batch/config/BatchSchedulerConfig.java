package com.poc.process.batch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class BatchSchedulerConfig {

    private static final Logger LOG = LoggerFactory.getLogger(BatchSchedulerConfig.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Scheduled(cron="*/10 * * * * *")
    public void defaultScheduler(){
        JobParameters jobParameters = new JobParametersBuilder().addLong("time",
                System.currentTimeMillis()).toJobParameters();

        try {
            JobExecution jobExecution = jobLauncher.run(job, jobParameters);

            LOG.info("Job's Status:::{}", jobExecution.getStatus());

        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException e) {
            LOG.error("Failed to execute job.");
        }
    }
}
