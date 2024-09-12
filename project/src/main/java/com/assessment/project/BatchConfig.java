package com.assessment.project;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.assessment.project.entity.Transaction;
import com.assessment.project.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableBatchProcessing(dataSourceRef = "batchDataSource", transactionManagerRef = "batchTransactionManager")
@RequiredArgsConstructor
public class BatchConfig extends DefaultBatchConfiguration {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private Transaction transaction;

    @Autowired
    private JobRepository jobRepository;
    
    @Autowired
    private ItemReader<Transaction> transactionItemReader;


    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public Transaction transaction() {
        return new Transaction();
    }


    @Bean
    public Job importVistorsJob() {
        return new JobBuilder("importVistorsJob", jobRepository)
                .start(importVistorsStep(jobRepository, transaction, transactionManager))
                .build();
    }

    @Bean
    public Step importVistorsStep(JobRepository jobRepository, Transaction transaction, PlatformTransactionManager transactionManager) {
        return new StepBuilder("importVistorsStep", jobRepository)
                .<Transaction, Transaction>chunk(100, transactionManager)
                .reader(transactionItemReader)
                .processor(itemProcessor())
                .writer(writer())
                .build();
    }

    @Bean
    public ItemProcessor<Transaction,Transaction> itemProcessor() {
    	
        return transaction ->transaction;    		
    }

    @Bean
    public ItemWriter<Transaction> writer() {
        return transactionRepository::saveAll;
    }

    @Bean
    public FlatFileItemReader<Transaction> flatFileItemReader(){
        FlatFileItemReader<Transaction> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setName("recordItemReader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/dataSource.txt"));
        flatFileItemReader.setLineMapper(linMappe());
        return flatFileItemReader;
    }
    @Bean
    public LineMapper<Transaction> linMappe() {
        DefaultLineMapper<Transaction> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter("|");
        lineTokenizer.setNames("accountNumber","trxAmount","description","trxDate","trxTime","customerId");
        lineTokenizer.setStrict(false); // Set strict property to false
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setTargetType(Transaction.class);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
        return defaultLineMapper;

    }
    
//    @Bean
//    public JdbcBatchItemWriter<Transaction> writer(DataSource dataSource) {
//        JdbcBatchItemWriter<Transaction> writer = new JdbcBatchItemWriter<>();
//        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
//        writer.setSql("INSERT INTO transaction_history (account_number, trx_amount, description, trx_date, trx_time, customer_id) " +
//                "VALUES (:accountNumber, :trxAmount, :description, :trxDate, :trxTime, :customerId)");
//        writer.setDataSource(dataSource);
//        return writer;
//    }
    
    

}