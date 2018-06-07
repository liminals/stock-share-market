// has access to bank accounts, and broker accounts and the stock prices of game
// the middle man between all the players and the bank
// maintains broker as well as bank transactions
package com.liminal.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.liminal.dao.BankDAO;
import com.liminal.dao.BrokerDAO;
import com.liminal.dao.GameDAO;
import com.liminal.model.BankAccount;
import com.liminal.model.BankTransaction;
import com.liminal.model.BrokerAccount;
import com.liminal.model.BrokerTransaction;
import com.liminal.model.Portfolio;
import com.liminal.model.Stock;
import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

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
				transaction.setTurn(req.getTurn());
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
				
				float newBankBalance = currentBankBalance - value;
				
				// add portfolio
				addToPortfolio(transaction);
				
				brokerDAO.updateTransactions(this.account);
				BankTransaction bt = createBankTransaction(transaction);
				updateBankAccount(bt, newBankBalance);
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
	
	// broker recieves SELL from client
	public BrokerTransaction sell(BrokerTransaction req, int gameid) {
		List<Stock> lsStocks = gameDAO.getCurrentPricesofStock(gameid);
		List<Portfolio> portfolios = getPortfolioFromDB(account.getName());
		Portfolio p = getPortfolio(portfolios, req.getStock());
		
		if (checkIsPriceMatches(lsStocks, req)) {
			if (p.getQty() >= req.getQty()) {
				BrokerTransaction transaction = new BrokerTransaction();
				transaction.setTurn(req.getTurn());
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
				
				// remove from portfolio
				if (p.getQty() == req.getQty()) {
					portfolios.remove(p);
				} else {
					// update in portfolio
					editInPortfolio(transaction, p);
				}
				account.setPortfolio(portfolios);
				
				brokerDAO.updateTransactions(this.account);
				BankTransaction bt = createBankTransaction(transaction);
				float newBankBalance = bankAccount.getCurrent_balance() + bt.getAmount();
				updateBankAccount(bt, newBankBalance);
				return transaction;
			} else {
				req.setStatus(BrokerTransaction.TYPE.SELL.toString());
				req.setStatus(BrokerTransaction.STATUS.INSUFFICIENT_STOCKS.toString());
				return req;
			}
		} else {
			req.setStatus(BrokerTransaction.TYPE.SELL.toString());
			req.setStatus(BrokerTransaction.STATUS.PRICE_DO_NOT_MATCH.toString());
			return req;
		}
	}
	
	// checks if the req price is equal as the current price in DB
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
	
	/////// BankAccount realted
	// creates and returns a bank transaction
	private BankTransaction createBankTransaction(BrokerTransaction transaction) {
		BankTransaction bt = new BankTransaction();
		bt.setTurn(transaction.getTurn());
		float value = transaction.getQty() * transaction.getPrice();
		if (transaction.getType().equalsIgnoreCase(BrokerTransaction.TYPE.BUY.toString())) {
			bt.setType(BankTransaction.TYPE.WITHDRAW.toString());
			bt.setReceiver("BROKER");
		} else {
			bt.setType(BankTransaction.TYPE.DEPOSIT.toString());
			bt.setReceiver("BROKER");
		}
		bt.setAmount(value);
		return bt;
	}
	
	// updates the bank account after transaction
	private void updateBankAccount(BankTransaction bt, float bankBalance) {
		List<BankTransaction> lbt;
		if (bankAccount.getTransactions() != null) {
			lbt = bankAccount.getTransactions();
			lbt.add(bt);
		} else {
			lbt = new ArrayList<>();
			lbt.add(bt);
		}
		bankAccount.setTransactions(lbt);
		bankAccount.setCurrent_balance(bankBalance);
		bankDAO.updateAccountAfterTransaction(bankAccount);
	}
	
	
	//////////////// PortfolioRelated
	private void addToPortfolio(BrokerTransaction transaction) {
		Portfolio p;
		
		List<Portfolio> port = account.getPortfolio();
		// portfolio is empty
		if (port == null) {
			port = new ArrayList<>();
			p = new Portfolio();
			p.setName(transaction.getStock());
			p.setValue(transaction.getPrice() * transaction.getQty());
			p.setQty(transaction.getQty());
			port.add(p);
		// portfolio is not empty
		} else {
			p = getPortfolio(port, transaction.getStock());
			// exists in portfolio
			if (p != null) {
				float currentValue = p.getValue();
				float transValue = transaction.getPrice() * transaction.getQty();
				float newValue = currentValue + transValue;
				int oldQty = p.getQty();
				int newQty = oldQty + transaction.getQty();
				p.setValue(newValue);
				p.setQty(newQty);
			// not exists in protfolio
			} else {
				p = new Portfolio();
				p.setName(transaction.getStock());
				p.setValue(transaction.getPrice() * transaction.getQty());
				p.setQty(transaction.getQty());
				port.add(p);
			}
		}
		account.setPortfolio(port);
	}
	
	// reduces the selling qty from portfolio
	private void editInPortfolio(BrokerTransaction transaction, Portfolio p) {
		int newQty = p.getQty() - transaction.getQty();
		
		float currentValue = p.getValue();
		float newValue = currentValue - (transaction.getQty() * transaction.getPrice());
		p.setQty(newQty);
		p.setValue(newValue);
	}
	
	private Portfolio getPortfolio(List<Portfolio> port, String s) {
		for (Portfolio p : port) {
			if (p.getName().equalsIgnoreCase(s)) {
				return p;
			}
		}
		return null;
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
	
	public List<Portfolio> getPortfolioFromDB(String player) {
		return this.brokerDAO.getPortfolio(player);
	}
	
	public List<BrokerTransaction> getTransactionsFromDB(String player) {
		return this.brokerDAO.getTransactions(player);
	}
}
