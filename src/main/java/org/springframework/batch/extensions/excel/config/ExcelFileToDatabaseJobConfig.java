package org.springframework.batch.extensions.excel.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.domain.StudentEntity;
import org.springframework.batch.extensions.excel.dto.StudentDTO;
import org.springframework.batch.extensions.excel.mapping.BeanWrapperRowMapper;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.batch.extensions.excel.processor.LoggingStudentProcessor;
import org.springframework.batch.extensions.excel.writer.LoggingStudentWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class ExcelFileToDatabaseJobConfig {

    @Bean
    ItemReader<StudentDTO> excelStudentReader() {
        PoiItemReader<StudentDTO> reader = new PoiItemReader<>();
        reader.setLinesToSkip(1);
        reader.setResource(new ClassPathResource("students.xlsx"));
        reader.setRowMapper(excelRowMapper());
        return reader;
    }

    private RowMapper<StudentDTO> excelRowMapper() {
        BeanWrapperRowMapper<StudentDTO> rowMapper = new BeanWrapperRowMapper<>();
        rowMapper.setTargetType(StudentDTO.class);
        return rowMapper;
    }

    /**
     * If your Excel document has no header, you have to create a custom
     * row mapper and configure it here.
     */
    /*private RowMapper<StudentDTO> excelRowMapper() {
       return new StudentExcelRowMapper();
    }*/
    @Bean
    ItemProcessor<StudentDTO, StudentEntity> excelStudentProcessor() {
        return new LoggingStudentProcessor();
    }

    @Bean
    ItemWriter<StudentEntity> excelStudentWriter() {
        return new LoggingStudentWriter();
    }

    @Bean
    Step excelFileToDatabaseStep(ItemReader<StudentDTO> excelStudentReader,
                                 ItemProcessor<StudentDTO, StudentEntity> excelStudentProcessor,
                                 ItemWriter<StudentEntity> excelStudentWriter,
                                 StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("excelFileToDatabaseStep")
                .<StudentDTO, StudentEntity>chunk(2)
                .reader(excelStudentReader)
                .processor(excelStudentProcessor)
                .writer(excelStudentWriter)
                .listener(promotionListener())
                .build();
    }

    @Bean
    Job excelFileToDatabaseJob(JobBuilderFactory jobBuilderFactory,
                               @Qualifier("excelFileToDatabaseStep") Step excelStudentStep, JobCompletionNotificationListener listener) {

        return jobBuilderFactory.get("excelFileToDatabaseJob")
                .incrementer(new RunIdIncrementer())
                //.flow(excelStudentStep)
                //.end()
                .listener(listener)
                .start(excelStudentStep)
                .build();
    }

    @Bean
    public ExecutionContextPromotionListener promotionListener() {
        System.out.println("promotionListener ->>>>>>>>");
        ExecutionContextPromotionListener listener = new ExecutionContextPromotionListener();

        listener.setKeys(new String[]{"lastSavedStudent"});

        return listener;
    }

}
