package com.liminal.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.liminal.controller.BankController;
import com.liminal.model.BankAccount;
import com.liminal.model.BankTransaction;

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
	
	@POST
	@Path("/deposit")
	public void deposit(BankTransaction transaction) {
		
	}
}
