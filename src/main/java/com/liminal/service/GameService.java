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
import com.liminal.controller.GameTimer;
import com.liminal.dao.GameSingleton;
import com.liminal.model.ClientTurn;
import com.liminal.model.Event;
import com.liminal.model.Game;
import com.liminal.model.Stock;

@Singleton
@Path("/game")
public class GameService {
	
	GameSingleton gameSingleton;
	
	
	@POST
	@Path("/start/{turns}/")
	public ClientTurn startGame(@PathParam("turns") int turns) {
		gameSingleton = GameSingleton.getInstance();
		Game game = new Game();
		game.setId(100);
		game.setTurns(turns);
		game.setCurrentTurn(1);
		game.setGameTimer(new GameTimer(game));
		
		GameController gameController = new GameController(game);
		gameController.loadStocksFromDB();
		
		gameSingleton.addGame(game);
				
		// create a timer object, only first player can access this service, for tomorrow
		ClientTurn ct = new ClientTurn();
		ct.setGameId(100);
		ct.setCurrentTurn(game.getCurrentTurn());
		ct.setTotalTurns(game.getTurns());
		return ct;
	}
	
	@POST
	@Path("/{gameid}/getNewPrice")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Stock> getMarketTrend(@PathParam("gameid") int gameid, List<Stock> stocks) {
		Game game = gameSingleton.getGame(gameid);
		GameController gc = new GameController(game);
		for (Stock s : game.getStocks())
			gc.getUpdatedStock(s);
		return stocks;
	}
	
	// this should happen in the controller
	// with a timer that auto updates the current turn
	// client should always check for change in turn
	// clock now()
	@POST
	@Path("/{gameid}/updateTurn")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ClientTurn updateTurn(@PathParam("gameid") int gameid, ClientTurn ct) {
		Game game = gameSingleton.getGame(gameid);
		int currentTurn = game.getCurrentTurn();
		ct.setCurrentTurn(currentTurn);
		return ct;
	}
	
	// check for events
	@GET
	@Path("/{gameid}/event")
	public Event checkForEvents(@PathParam("gameid") int gameid) {
		Game game = gameSingleton.getGame(gameid);
		Event e = game.getCurrentEvent(); 
		if (e != null) {
			return e;
		}
		return null;
	}
	
	@GET
	@Path("/{gameid}/turn/{turn}")
	public boolean canRequest(@PathParam("gameid") int gameid, @PathParam("turn") int turn) {
		Game game = gameSingleton.getGame(gameid);
		if (turn == game.getCurrentTurn()) {
			return false;
		}
		return true;
	}
	
	
	@POST
	@Path("/{gameid}/end")
	public void endGame() {
		gameSingleton.destroyInstance();
	}
}
