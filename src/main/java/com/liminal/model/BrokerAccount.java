package com.liminal.model;

import java.util.List;

public class BrokerAccount {
	private String name;
	private List<Portfolio> portfolio;
	private List<BrokerTransaction> transactions;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Portfolio> getPortfolio() {
		return portfolio;
	}
	public void setPortfolio(List<Portfolio> portfolio) {
		this.portfolio = portfolio;
	}
	public List<BrokerTransaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<BrokerTransaction> transactions) {
		this.transactions = transactions;
	}
	@Override
	public String toString() {
		return "BrokerAccount [name=" + name + ", portfolio=" + portfolio + ", transactions=" + transactions + "]";
	}
}
