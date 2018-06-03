package com.liminal.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.liminal.controller.BankController;
import com.liminal.controller.BrokerController;
import com.liminal.model.BankAccount;
import com.liminal.model.BrokerAccount;
import com.liminal.model.BrokerTransaction;

@Path("/broker")
public class BrokerService {
	
	private BrokerController controller;
	private BankController bankController;
	
	@POST
	@Path("/createAccount/{name}")
	public void createAccount(@PathParam("name") String name) {
		controller = new BrokerController();
		controller.createAccount(name);
	}
	
	// setup player account before each game
	@POST
	@Path("/setAccount/{name}")
	public void setBrokerAccountForGame(@PathParam("name") String name) {
		controller = new BrokerController();
		BrokerAccount account = controller.getAccountFromDB(name);
		controller.setAccount(account);
		controller.setAccountForGame();
	}

	@POST
	@Path("/buy/{player}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BrokerTransaction buyStocks(BrokerTransaction transaction, @PathParam("player") String player) {
		bankController = new BankController();
		controller = new BrokerController();
		
		BankAccount bankAccount = bankController.getAccountFromDB(player);
		controller.setBankAccount(bankAccount);
		
		BrokerAccount account = controller.getAccountFromDB(player);
		controller.setAccount(account);
		return controller.buy(transaction);
	}
	
	@POST
	@Path("/sell/{player}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BrokerTransaction sellStocks(BrokerTransaction transaction, @PathParam("player") String player) {
		bankController = new BankController();
		controller = new BrokerController();
		
		BankAccount bankAccount = bankController.getAccountFromDB(player);
		controller.setBankAccount(bankAccount);
		
		BrokerAccount account = controller.getAccountFromDB(player);
		controller.setAccount(account);
		return controller.sell(transaction);
	}
}