package com.poc.process.batch.config;

import com.poc.process.batch.model.Person;
import com.poc.process.batch.model.PersonRowMapper;
import com.poc.process.batch.steps.PersonItemProcessor;
import com.poc.process.batch.steps.PersonWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private static final Logger LOG = LoggerFactory.getLogger(BatchConfig.class);

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Value("${poc.process.person.chunk.size}")
    private Integer chunkSize;

    @Autowired
    @Qualifier("mySqlDataSource")
    private DataSource dataSource;

    @Bean
    public JobRepository personJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(personTransactionManager());
        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager personTransactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean
    public JdbcCursorItemReader<Person> reader(){
        LOG.info("--- Starting user item reader ---");

        JdbcCursorItemReader<Person> cursorItemReader = new JdbcCursorItemReader<>();

        cursorItemReader.setDataSource(dataSource);
        cursorItemReader.setSql("SELECT person_id,first_name,last_name,email,age FROM poc_process_batch.person");
        cursorItemReader.setRowMapper(new PersonRowMapper());

        return cursorItemReader;
    }

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    @Bean
    public ItemWriter<Person> writer() {
        return new PersonWriter();
    }

    @Bean
    public Step processPersonStep() {
        return stepBuilderFactory.get("processPersonStep")
                .transactionManager(personTransactionManager())
                .<Person, Person>chunk(chunkSize)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job personProcessJob() throws Exception {
        return jobBuilderFactory.get("personProcessJob")
                .repository(personJobRepository())
                .start(processPersonStep())
                .build();
    }
}
