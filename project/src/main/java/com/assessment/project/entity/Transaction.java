package com.assessment.project.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "transaction_history", schema = "test")
@Data
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@Column(name = "account_number")
	String accountNumber;
	
	@Column(name = "trx_amount")
    BigDecimal trxAmount;
	
	@Column(name = "description")
    String description;
	
	@Column(name = "trx_date")
    String trxDate;
	
	@Column(name = "trx_time")
    String trxTime;
	
	@Column(name = "customer_id")
    String customerId;

}
