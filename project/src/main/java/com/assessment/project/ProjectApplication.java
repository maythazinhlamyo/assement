package com.assessment.project;

import java.util.Calendar;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectApplication implements CommandLineRunner {
	
	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;
   
	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws JobInstanceAlreadyCompleteException, 
	JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
		JobParameters jobParameters = new JobParametersBuilder()
				.addDate("timestamp", Calendar.getInstance().getTime())
				.toJobParameters();
		JobExecution jobExecution = jobLauncher.run(job, jobParameters);
		while (jobExecution.isRunning()){
			System.out.println("..................");
		}
		//return jobExecution.getStatus();
	}
}
