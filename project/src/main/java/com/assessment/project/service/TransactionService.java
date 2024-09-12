package com.assessment.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.assessment.project.entity.Transaction;
import com.assessment.project.repository.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	public Page<Transaction> getTransactions(String customerId, String accountNumber, String description, Pageable pageable) {
		return transactionRepository.findByCustomerIdOrAccountNumberOrDescription(customerId, accountNumber, description, pageable);
	}

	@Transactional
	public Transaction updateTransaction(Long id, String newDescription) {
		Transaction transaction = transactionRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Transaction not found"));

		transaction.setDescription(newDescription);
		return transactionRepository.save(transaction); // Will check version field to handle concurrent updates
	}
}
