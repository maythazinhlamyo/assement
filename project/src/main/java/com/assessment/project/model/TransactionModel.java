package com.assessment.project.model;

public class TransactionModel {
	private String accountNumber;
    private String trxAmount;
    private String description;
    private String trxDate;
    private String trxTime;
    private String customerId;

    // Getters and Setters

    @Override
    public String toString() {
        return "TransactionModel{" +
               "accountNumber='" + accountNumber + '\'' +
               ", trxAmount=" + trxAmount +
               ", description='" + description + '\'' +
               ", trxDate='" + trxDate + '\'' +
               ", trxTime='" + trxTime + '\'' +
               ", customerId='" + customerId + '\'' +
               '}';
    }

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getTrxAmount() {
		return trxAmount;
	}

	public void setTrxAmount(String trxAmount) {
		this.trxAmount = trxAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTrxDate() {
		return trxDate;
	}

	public void setTrxDate(String trxDate) {
		this.trxDate = trxDate;
	}

	public String getTrxTime() {
		return trxTime;
	}

	public void setTrxTime(String trxTime) {
		this.trxTime = trxTime;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
    
    
}
