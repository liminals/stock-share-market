// has access to bank accounts, and broker accounts and the stock prices of game
package com.liminal.controller;

import java.util.ArrayList;
import java.util.List;

import com.liminal.dao.BankDAO;
import com.liminal.dao.BrokerDAO;
import com.liminal.dao.GameDAO;
import com.liminal.model.BankAccount;
import com.liminal.model.BrokerAccount;
import com.liminal.model.BrokerTransaction;
import com.liminal.model.Stock;

public class BrokerController {
	private BrokerAccount account;
	private BankAccount bankAccount;
	private BrokerDAO brokerDAO;
	private BankDAO bankDAO;
	private GameDAO gameDAO;
	
	public BrokerController() {
		this.brokerDAO = new BrokerDAO();
		this.bankDAO = new BankDAO();
		this.bankDAO = new BankDAO();
		this.gameDAO = new GameDAO();
	}
	public BrokerAccount getAccount() {
		return account;
	}
	public void setAccount(BrokerAccount account) {
		this.account = account;
	}
	public BankAccount getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	// broker recieves BUY from client
	public BrokerTransaction buy(BrokerTransaction req, int gameid) {
		float currentBankBalance = bankAccount.getCurrent_balance();
		List<Stock> lsStocks = gameDAO.getCurrentPricesofStock(gameid);
		if (checkIsPriceMatches(lsStocks, req)) {
			float value = req.getQty() * req.getPrice();
			if (value < currentBankBalance) {
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
			} else {
				req.setStatus(BrokerTransaction.TYPE.BUY.toString());
				req.setStatus(BrokerTransaction.STATUS.INSUFFICIENT_FUNDS.toString());
				return req;
			}
		} else {
			req.setStatus(BrokerTransaction.TYPE.BUY.toString());
			req.setStatus(BrokerTransaction.STATUS.PRICE_DO_NOT_MATCH.toString());
			return req;
		}
	}
	
	public BrokerTransaction sell(BrokerTransaction req, int gameid) {
		List<Stock> lsStocks = gameDAO.getCurrentPricesofStock(gameid);
		if (checkIsPriceMatches(lsStocks, req)) {
			BrokerTransaction transaction = new BrokerTransaction();
			transaction.setStock(req.getStock());
			transaction.setQty(req.getQty());
			transaction.setType(BrokerTransaction.TYPE.SELL.toString());
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
		} else {
			req.setStatus(BrokerTransaction.TYPE.SELL.toString());
			req.setStatus(BrokerTransaction.STATUS.PRICE_DO_NOT_MATCH.toString());
			return req;
		}
	}
	
	
	private boolean checkIsPriceMatches(List<Stock> ls, BrokerTransaction bt) {
		boolean match = true;
		for (Stock s : ls) {
			if (s.getName().equalsIgnoreCase(bt.getStock())) {
				if (s.getCurrent_price() != bt.getPrice()) {
					match = false;
				}
				break;
			}
		}
		return match;
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
