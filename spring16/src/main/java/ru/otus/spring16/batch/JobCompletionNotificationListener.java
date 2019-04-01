package ru.otus.spring16.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import ru.otus.spring16.batch.domain.BookSQL;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
    private CrudRepository<BookSQL, Long> crudRepository;

    public JobCompletionNotificationListener(CrudRepository<BookSQL, Long> crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Assert results.");
            log.info("First record:" + crudRepository.findById(1L).toString());
            crudRepository.findById(1L).stream().map(bookSQL -> {
                log.info("First record: " + bookSQL.toString());
                return bookSQL.getReviews();
            }).forEach(reviewSQL -> log.info("Review: " + reviewSQL.toString()));
            log.info("Count: " + crudRepository.count());
        }
    }
}
