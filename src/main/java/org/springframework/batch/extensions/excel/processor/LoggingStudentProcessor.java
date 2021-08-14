package org.springframework.batch.extensions.excel.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.extensions.excel.domain.StudentEntity;
import org.springframework.batch.extensions.excel.dto.StudentDTO;
import org.springframework.batch.item.ItemProcessor;

public class LoggingStudentProcessor implements ItemProcessor<StudentDTO, StudentEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingStudentProcessor.class);

    @Override
    public StudentEntity process(StudentDTO item) {
        LOGGER.info("Processing student information: {}", item);
        StudentEntity entity = new StudentEntity();
//        entity.setId(item.getId(¬));
        entity.setEmailAddress(item.getEmailAddress());
        entity.setPurchasedPackage(item.getPurchasedPackage());
        entity.setName(item.getName());
        return entity;
    }
}
