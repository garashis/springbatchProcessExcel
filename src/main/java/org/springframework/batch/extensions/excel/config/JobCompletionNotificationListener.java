package org.springframework.batch.extensions.excel.config;


import org.springframework.batch.extensions.excel.domain.StudentRepo;
import org.springframework.batch.extensions.excel.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
    private final StudentRepo studentRepo;

    @Autowired
    public JobCompletionNotificationListener(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");
            //System.out.println(personRepository.findAll().);
            studentRepo.findAll()
                    .forEach(student -> log.info("Found <" + student + "> in the database.") );
        }
    }
}
