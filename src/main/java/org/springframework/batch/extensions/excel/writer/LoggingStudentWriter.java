package org.springframework.batch.extensions.excel.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.extensions.excel.domain.StudentEntity;
import org.springframework.batch.extensions.excel.domain.StudentRepo;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LoggingStudentWriter implements ItemWriter<StudentEntity> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingStudentWriter.class);
    private StepExecution stepExecution;
    @Autowired
    private StudentRepo studentRepo;

    @Override
    public void write(List<? extends StudentEntity> studentEntities) throws Exception {
        LOGGER.info("Received the information of {} students", studentEntities.size());

        ExecutionContext stepContext = stepExecution.getExecutionContext();
        if(stepContext.containsKey("lastSavedStudent")){
            LOGGER.info("Found Last Saved Student : " + stepContext.get("lastSavedStudent"));
        } else {
            LOGGER.info("Not Found Last Saved Student");
        }

        //Save to DB
        studentRepo.deleteAll();
        studentRepo.saveAll(studentEntities);
        // Put last saved student in ExecutionContext
        stepContext.put("lastSavedStudent", studentEntities.get(studentEntities.size() - 1));
    }

    @BeforeStep
    public void retrieveLastSavedStudent(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }
}
