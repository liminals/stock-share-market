package com.liminal.controller;

import java.util.ArrayList;
import java.util.List;

import com.liminal.dao.BrokerDAO;
import com.liminal.model.BrokerAccount;
import com.liminal.model.BrokerTransaction;

public class BrokerController {
	private BrokerAccount account;
	private BrokerDAO brokerDAO;
	
	public BrokerController() {
		this.brokerDAO = new BrokerDAO(); 
	}
	public BrokerAccount getAccount() {
		return account;
	}
	public void setAccount(BrokerAccount account) {
		this.account = account;
	}

	public BrokerTransaction buy(BrokerTransaction req) {
		BrokerTransaction transaction = new BrokerTransaction();
		transaction.setStock(req.getStock());
		transaction.setQty(req.getQty());
		transaction.setType(BrokerTransaction.TYPE.BUY.toString());
		transaction.setPrice(req.getPrice());
		
		if (this.account.getTransactions() != null) {
			this.account.getTransactions().add(transaction);
		} else {
			List<BrokerTransaction> trans = new ArrayList<>();
			trans.add(transaction);
			this.account.setTransactions(trans);
		}
		brokerDAO.updateTransactions(this.account);
		return transaction;
	}
	
	
	
	
	
	/////////////////////////// DB actions
	public void createAccount(String name) {
		BrokerAccount account = new BrokerAccount();
		account.setName(name);
		setAccount(account);
		brokerDAO.createAccount(account);
	}
	
	// returns an account from database
	public BrokerAccount getAccountFromDB(String name) {
		return brokerDAO.getAccount(name);
	}
	
	// setup the bank account for each game at the DB
	public void setAccountForGame() {
		this.brokerDAO.setAccountForGame(account);
	}
}
