package com.assessment.project.controller;

import java.util.Calendar;
import java.util.Optional;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.project.entity.Transaction;
import com.assessment.project.model.TransactionModel;
import com.assessment.project.repository.TransactionRepository;
import com.assessment.project.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class ProjectController {

	    @Autowired
	    private TransactionRepository transactionRepository;
	//
	//    @GetMapping
	//    public Page<Transaction> getTransactions(
	//            @RequestParam(value = "customer_id", required = false) String customerId,
	//            @RequestParam(value = "account_number", required = false) String accountNumber,
	//            @RequestParam(value = "description", required = false) String description,
	//            @RequestParam(value = "page", defaultValue = "0") int page,
	//            @RequestParam(value = "size", defaultValue = "10") int size) {
	//        return transactionRepository.findAll(PageRequest.of(page, size));
	//    }
	//
	//    @PutMapping("/{id}")
	//    public ResponseEntity<?> updateTransaction(@PathVariable Long id, @RequestBody Transaction updatedTransaction) {
	//        Optional<Transaction> existingTransaction = transactionRepository.findById(id);
	//        if (existingTransaction.isPresent()) {
	//            // Handle concurrency using optimistic locking or other methods
	//        	Transaction transaction = existingTransaction.get();
	//            transaction.setDescription(updatedTransaction.getDescription());
	//            return ResponseEntity.ok(transactionRepository.save(transaction));
	//        }
	//        return ResponseEntity.notFound().build();
	//    }

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;

	@Autowired
	private TransactionService transactionService;

	@GetMapping
	public Page<Transaction> getTransactions(
			@RequestParam(required = false) String customerId,
			@RequestParam(required = false) String accountNumber,
			@RequestParam(required = false) String description,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		Pageable pageable = PageRequest.of(page, size);
		return transactionService.getTransactions(customerId, accountNumber, description, pageable);
	}

	@PutMapping("/{id}")
	public Transaction updateTransaction(
			@PathVariable Long id, 
			@RequestBody String newDescription) {
		return transactionService.updateTransaction(id, newDescription);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateTransaction2(@PathVariable String id, @RequestBody Transaction updatedTransaction) {
		Optional<Transaction> existingTransaction = transactionRepository.findById(Long.valueOf(id));
		if (existingTransaction.isPresent()) {
			// Handle concurrency using optimistic locking or other methods
			Transaction transaction = existingTransaction.get();
			transaction.setDescription(updatedTransaction.getDescription());
			return ResponseEntity.ok(transactionRepository.save(transaction));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/runJob") 
	public BatchStatus load() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
		JobParameters jobParameters = new JobParametersBuilder()
				.addDate("timestamp", Calendar.getInstance().getTime())
				.toJobParameters();
		JobExecution jobExecution = jobLauncher.run(job, jobParameters);
		while (jobExecution.isRunning()){
			System.out.println("..................");
		}
		return jobExecution.getStatus();
	}
}
