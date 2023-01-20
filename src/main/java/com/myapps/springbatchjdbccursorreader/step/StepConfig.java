package com.myapps.springbatchjdbccursorreader.step;

import com.myapps.springbatchjdbccursorreader.domain.Customer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StepConfig {

    private final StepBuilderFactory stepBuilderFactory;

    public StepConfig(StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Step jdbcCursorStep(
        ItemReader<Customer> jdbcCursorReader,
        ItemWriter<Customer> jdbcCursorWriter
    ) {
        return stepBuilderFactory
            .get("jdbcCursorStep")
            .<Customer, Customer>chunk(5)
            .reader(jdbcCursorReader)
            .writer(jdbcCursorWriter)
            .build();
    }
}
