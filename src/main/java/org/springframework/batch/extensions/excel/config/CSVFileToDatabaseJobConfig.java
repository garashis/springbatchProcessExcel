package org.springframework.batch.extensions.excel.config;

import org.springframework.batch.extensions.excel.domain.Person;
import org.springframework.batch.extensions.excel.processor.PersonItemProcessor;
import org.springframework.batch.extensions.excel.repository.PersonRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class CSVFileToDatabaseJobConfig  {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Lazy
    public PersonRepository personRepository;

    @Bean
    public FlatFileItemReader<Person> reader() {
        return new FlatFileItemReaderBuilder<Person>()
                .name("personItemReader")
                .resource(new ClassPathResource("data.csv"))
                .delimited()
                .names(new String[]{"id", "firstName", "lastName"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                    setTargetType(Person.class);
                }})
                .build();
    }



    @Bean
    public RepositoryItemWriter<Person> writer() {
        RepositoryItemWriter<Person> writer = new RepositoryItemWriter<>();
        writer.setRepository(personRepository);
        //writer.setMethodName("save");
        return writer;
    }

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    @Bean
    public Step step1(ItemReader<Person> itemReader, ItemWriter<Person> itemWriter)
            throws Exception {

        return stepBuilderFactory.get("step1")
                .<Person, Person>chunk(5)
                .reader(itemReader)
                .processor(processor())
                .writer(itemWriter)
                .build();
    }

    @Bean
    public Job personUpdateJob(JobCompletionNotificationListener listener, Step step1)
            throws Exception {

        return this.jobBuilderFactory.get("personUpdateJob").incrementer(new RunIdIncrementer())
                .listener(listener).start(step1).build();
    }

}
