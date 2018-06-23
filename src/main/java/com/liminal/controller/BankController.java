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
	
	/////////////////////////// DB actions
	public void createAccount(String name) {
		boolean found = bankDAO.checkAccount(name);
		if(!found) {
			BankAccount account = new BankAccount();
			account.setName(name);
			setAccount(account);
			bankDAO.createAccount(account);
		} else {
			BankAccount account = new BankAccount();
			account.setName(null);
			account.setCurrent_balance(0);
			setAccount(account);
		}
	}
	
	// returns an account from database
	public BankAccount getAccountFromDB(String name) {
		return bankDAO.getAccount(name);
	}
	
	// returns the updated balance from database
	public void getBalance() {
		this.bankDAO.getBalance(account);
	}
	
	// setup the bank account for each game at the DB
	public void setAccountForGame() {
		this.bankDAO.setAccountForGame(account);
	}
}
