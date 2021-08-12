package org.springframework.batch.extensions.excel.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.extensions.excel.domain.StudentEntity;
import org.springframework.batch.extensions.excel.domain.StudentRepo;
import org.springframework.batch.extensions.excel.dto.StudentDTO;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LoggingStudentWriter implements ItemWriter<StudentEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingStudentWriter.class);

    @Autowired private StudentRepo repo;

    @Override
    public void write(List<? extends StudentEntity>items) throws Exception {
        LOGGER.info("Received the information of {} students", items.size());
        repo.saveAll(items);
        items.forEach(i -> LOGGER.debug("Received the information of a student: {}", i));
    }
}
