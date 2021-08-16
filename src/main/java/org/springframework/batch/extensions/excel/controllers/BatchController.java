package org.springframework.batch.extensions.excel.controllers;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.extensions.excel.repository.RepoForMultipleTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/batch")
public class BatchController {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    @Qualifier("personUpdateJob")
    private Job personUpdateJob;
    @Autowired
    @Qualifier("excelFileToDatabaseJob")
    private Job excelFileToDatabaseJob;

    @Autowired private RepoForMultipleTable repoForMultipleTable;

    @GetMapping(path = "/personUpdateJob")
    public ResponseEntity<String> startBatch() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(personUpdateJob, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException
                | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {

            e.printStackTrace();
        }
        return new ResponseEntity<>("Batch Process started!!", HttpStatus.OK);
    }

    @GetMapping(path = "/excelFileToDatabaseJob/{id}")
    public ResponseEntity<String> student(@PathVariable int id) {
        System.out.println(repoForMultipleTable.getDataFromMultipleTables(id));

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(excelFileToDatabaseJob, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException
                | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {

            e.printStackTrace();
        }
        return new ResponseEntity<>("Batch Process completed!!", HttpStatus.OK);
    }
}
