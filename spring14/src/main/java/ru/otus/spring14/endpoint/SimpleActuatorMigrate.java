package ru.otus.spring14.endpoint;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "migrate")
public class SimpleActuatorMigrate {

    private JobLauncher jobLauncher;
    private Job importUserJob;

    public SimpleActuatorMigrate(JobLauncher jobLauncher, Job importUserJob) {
        this.jobLauncher = jobLauncher;
        this.importUserJob = importUserJob;
    }

    @WriteOperation
    public void migrate() throws Exception{
        jobLauncher.run(importUserJob, new JobParametersBuilder().toJobParameters());
    }
}
