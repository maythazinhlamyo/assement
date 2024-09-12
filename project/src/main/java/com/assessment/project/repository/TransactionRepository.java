package com.assessment.project.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.assessment.project.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	Page<Transaction> findByCustomerIdOrAccountNumberOrDescription(
	        String customerId, String accountNumber, String description, Pageable pageable
	    );
}
