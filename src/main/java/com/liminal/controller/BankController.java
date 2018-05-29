package com.liminal.controller;

import com.liminal.dao.BankDAO;
import com.liminal.model.BankAccount;

public class BankController {
	private BankAccount account;
	private BankDAO bankDAO;
	
	public BankController() {
		this.bankDAO = new BankDAO();
	}
	
	public BankAccount getAccount() {
		return account;
	}

	public void setAccount(BankAccount account) {
		this.account = account;
	}

	public void createAccount(String name) {
		boolean found = bankDAO.checkAccount(name);
		if(!found) {
			BankAccount account = new BankAccount();
			account.setName(name);
			account.setCurrent_balance(1000);
			setAccount(account);
			bankDAO.createAccount(account);
		} else {
			BankAccount account = new BankAccount();
			account.setName(null);
			account.setCurrent_balance(0);
			setAccount(account);
		}
	}
}
