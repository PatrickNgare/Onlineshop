package com.filing.demo.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobScheduler {

    private final JobLauncher jobLauncher;
    private final JobBuilderFactory jobBuilders;
    private final Step taskletStep;
    private final Step chunkStep;

    @Scheduled(fixedRate = 5000)
    public void run() throws Exception {
        JobExecution execution = jobLauncher.run(customerReportJob(), new JobParametersBuilder().addLong("uniqueness", System.nanoTime()).toJobParameters());
        log.info("Exit status: {}", execution.getStatus());
    }

    @Bean
    public Job customerReportJob() {
        return jobBuilders.get("customerReportJob").start(taskletStep).next(chunkStep).build();
    }
}
