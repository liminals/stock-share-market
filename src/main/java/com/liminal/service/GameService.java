package com.liminal.service;

import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.liminal.controller.GameController;
import com.liminal.dao.GameSingleton;
import com.liminal.model.ClientTurn;
import com.liminal.model.Event;
import com.liminal.model.Stock;

@Singleton
@Path("/game")
public class GameService {
	
	GameSingleton gameSingleton;
	GameController gameController;
	
	@POST
	@Path("/start")
	public ClientTurn startGame() {	
		gameSingleton = GameSingleton.getInstance();
		gameSingleton.setGameTimer();
		gameController = new GameController();
		gameController.setGameSingleton(gameSingleton);
		gameController.loadStocksFromDB();
		gameSingleton.startGameTimer();
		
		// create a timer object, only first player can access this service, for tomorrow
		ClientTurn ct = new ClientTurn();
		ct.setCurrentTurn(gameSingleton.getGame().getCurrentTurn());
		ct.setTotalTurns(gameSingleton.getGame().getTurns());
		return ct;
	}
	
	@POST
	@Path("/getNewPrice")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Stock> getMarketTrend(List<Stock> stocks) {
		for (Stock s : stocks)
			gameController.getUpdatedStock(s);
		return stocks;
	}
	
	// this should happen in the controller
	// with a timer that auto updates the current turn
	// client should always check for change in turn
	// clock now()
	@POST
	@Path("/updateTurn")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ClientTurn updateTurn(ClientTurn ct) {
		int currentTurn = gameSingleton.getGame().getCurrentTurn();
		ct.setCurrentTurn(currentTurn);
		return ct;
	}
	
	// check for events
	@GET
	@Path("/event")
	public Event checkForEvents() {
		Event e = gameSingleton.getGame().getCurrentEvent(); 
		if (e != null) {
			return e;
		}
		return null;
	}
	
	@GET
	@Path("/turn/{turn}")
	public boolean canRequest(@PathParam("turn") int turn) {
		if (turn == this.gameSingleton.getGame().getCurrentTurn()) {
			return false;
		}
		return true;
	}
	
	
	@POST
	@Path("/end")
	public void endGame() {
		gameSingleton.destroyInstance();
	}
}
