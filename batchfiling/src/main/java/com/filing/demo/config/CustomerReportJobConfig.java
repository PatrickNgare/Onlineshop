package com.filing.demo.config;


import com.filing.demo.models.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CustomerReportJobConfig {


    private final StepBuilderFactory stepBuilders;
    private final JobExplorer jobs;
    public static final String XML_FILE = "database.xml";
    private static final String JOB_NAME = "customerReportJob";



    @Bean
    public Step taskletStep() {
        return stepBuilders.get("taskletStep").tasklet(tasklet()).build();
    }

    @Bean
    @Qualifier("tasklet")
    public Tasklet tasklet() {
        return (contribution, chunkContext) -> {
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    @Qualifier("chunkStep")
    public Step chunkStep() {
        return stepBuilders.get("chunkStep").<Customer, Customer>chunk(20).reader(reader()).processor(processor()).writer(writer()).build();
    }

    @StepScope
    @Bean
    public ItemWriter<Customer> writer() {
        return new CustomerItemWriter();
    }

    @StepScope
    @Bean
    public ItemReader<Customer> reader() {
        return new CustomerItemReader(XML_FILE);
    }

    @StepScope
    @Bean
    public ItemProcessor<Customer, Customer> processor() {
        final CompositeItemProcessor<Customer, Customer> processor = new CompositeItemProcessor<>();
        processor.setDelegates(Arrays.asList(new BirthdayFilterProcessor(), new TransactionValidatingProcessor(5)));
        return processor;
    }



    @PreDestroy
    public void destroy() throws NoSuchJobException {
        jobs.getJobNames().forEach(name -> log.info("job name: {}", name));
        jobs.getJobInstances(JOB_NAME, 0, jobs.getJobInstanceCount(JOB_NAME)).forEach(jobInstance -> {
            log.info("job instance id {}", jobInstance.getInstanceId());
        });

    }
}
