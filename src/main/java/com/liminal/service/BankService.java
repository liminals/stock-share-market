package com.liminal.service;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.liminal.controller.BankController;
import com.liminal.model.BankAccount;

@Path("/bank")
public class BankService {
	
	private BankController controller;
	
	// if response name=null account already exists for player
	@POST
	@Path("/createAccount/{name}")
	public BankAccount createAccount(@PathParam("name") String name) {
		controller = new BankController();
		controller.createAccount(name);
		return controller.getAccount();
	}
	
	@GET
	@Path("/getBalance/{name}")
	public BankAccount getBalance(@PathParam("name") String name) {
		controller = new BankController();
		BankAccount account = controller.getAccountFromDB(name);
		controller.setAccount(account);
		controller.getBalance();
		return controller.getAccount();
	}
	
	// setup player account before each game
	@POST
	@Path("/setAccount/{name}")
	public void setAccountForGame(@PathParam("name") String name) {
		controller = new BankController();
		BankAccount account = controller.getAccountFromDB(name);
		controller.setAccount(account);
		controller.setAccountForGame();
	} 
}
