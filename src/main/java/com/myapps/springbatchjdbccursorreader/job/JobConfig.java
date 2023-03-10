package com.myapps.springbatchjdbccursorreader.job;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class JobConfig {

    private final JobBuilderFactory jobBuilderFactory;

    public JobConfig(JobBuilderFactory jobBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    public Job jdbcCursorJob(
        Step jdbcCursorStep
    ) {
        return jobBuilderFactory
            .get("jdbcCursorJob")
            .start(jdbcCursorStep)
            .incrementer(new RunIdIncrementer())
            .build();
    }

}
