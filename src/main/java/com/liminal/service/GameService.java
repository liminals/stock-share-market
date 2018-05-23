package com.liminal.service;

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
import com.liminal.model.Stock;

@Singleton
@Path("/game")
public class GameService {
	
	GameSingleton gs;// = GameSingleton.getInstance();
	GameController gc;
	
	@POST
	@Path("/start")
	public void startGame() {
		gs = null;
		gc = null;
		gs = GameSingleton.getInstance();
		gc = new GameController();
		gc.setGameSingleton(gs);
	}
	
	// implement this
	@POST
	@Path("/getNewPrice")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Stock getMarketTrend(Stock s) {
		gc.updatePrice(s);
		return s;		
	}
	
	@GET
	@Path("/turn/{set}")
	public void setTurn(@PathParam("set") int set) {
		gs.setTurn(set);
	}
	
	@GET
	@Path("/turn")
	public int getTurn() {
		return gs.getTurn();
	}
}
