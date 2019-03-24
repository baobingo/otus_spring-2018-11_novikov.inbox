package ru.otus.spring14.batch;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.repository.CrudRepository;
import ru.otus.spring14.batch.domain.BookSQL;
import ru.otus.spring14.domain.Book;

import java.util.HashMap;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private final Logger logger = LoggerFactory.getLogger("batch");

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private CrudRepository<BookSQL, Long> crudRepository;
    private JobLauncher jobLauncher;

    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, CrudRepository<BookSQL, Long> crudRepository, JobLauncher jobLauncher) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.crudRepository = crudRepository;
        this.jobLauncher = jobLauncher;
    }

    @Bean
    public ItemReader<Book> reader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Book>().template(mongoTemplate).name("reader").jsonQuery("{}").targetType(Book.class).sorts(new HashMap<>()).build();
    }

    @Bean
    public BookItemProcessor processor() {
        return new BookItemProcessor();
    }

    @Bean
    public RepositoryItemWriter<BookSQL> writer() {
        return new RepositoryItemWriterBuilder<BookSQL>().methodName("save").repository(crudRepository).build();
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("BEGIN import user job");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("END import user job");
                    }
                })
                .listener(listener)
                .build();
    }

    @Bean
    public Step step1(ItemWriter<BookSQL> writer, ItemReader<Book> reader) {
        return stepBuilderFactory.get("step1")
                .<Book, BookSQL> chunk(5)
                .reader(reader)
                .processor(processor())
                .writer(writer)
                .build();
    }
}
