package com.liminal.model;

import java.util.List;

public class BankAccount {
	private String name;
	private float current_balance;
	private List<BankTransaction> transactions;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getCurrent_balance() {
		return current_balance;
	}
	public void setCurrent_balance(float current_balance) {
		this.current_balance = current_balance;
	}
	public List<BankTransaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<BankTransaction> transactions) {
		this.transactions = transactions;
	}
	@Override
	public String toString() {
		return "BankAccount [name=" + name + ", current_balance=" + current_balance + ", transactions=" + transactions
				+ "]";
	}
}
