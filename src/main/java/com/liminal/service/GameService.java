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

import org.json.JSONObject;

import com.liminal.controller.GameController;
import com.liminal.controller.GameTimer;
import com.liminal.dao.GameSingleton;
import com.liminal.model.ClientTurn;
import com.liminal.model.Event;
import com.liminal.model.Game;
import com.liminal.model.GameHostingData;
import com.liminal.model.GameJoinData;
import com.liminal.model.Player;
import com.liminal.model.Stock;

@Singleton
@Path("/game")
public class GameService {
	
	GameSingleton gameSingleton = GameSingleton.getInstance();
	
	// will return currently active games where status = yet_to_start
	@POST
	@Path("/getGames") 
	public List<Game> getGames() {
		return gameSingleton.getGamesYetToStart();
	}
	
	@POST
	@Path("/join")
	public GameJoinData joinGame(GameJoinData gameJoinData){
		if (gameJoinData.getStatus().equalsIgnoreCase(GameJoinData.STATUS.REQUESTING.toString())) {
			//return gameSingleton.checkForJoin(gameJoinData);
			GameJoinData gjd = gameSingleton.checkForJoin(gameJoinData);
			Game g = gameSingleton.getGame(gjd.getGameId());
			GameController gc = new GameController(g);
			gc.updatePlayersInDB();
			return gjd;
		}
		return gameJoinData;
	}
	
	@POST
	@Path("/isStarted")
	public ClientTurn isStarted(ClientTurn data){
		Game game = gameSingleton.getGame(data.getGameId());
		if (game.getStatus().equalsIgnoreCase(Game.STATUS.STARTED.toString())){
			data.setGame_status(ClientTurn.GAME_STATUS.STARTED.toString());
			data.setTotalTurns(game.getTurns());
			data.setCurrentTurn(game.getCurrentTurn());
		} else {
			data.setGame_status(ClientTurn.GAME_STATUS.YET_TO_START.toString());
		}
		return data;
	}
	
	// this will host a game
	@POST
	@Path("/create")
	public GameHostingData createGame(GameHostingData gameData) {
		Game game = new Game();
		
		game.setTurns(gameData.getTurns());
		game.setCurrentTurn(1);
		game.setCreatedBy(gameData.getCreatedBy());
		game.setGameTimer(new GameTimer(game));
		game.setStatus(Game.STATUS.YET_TO_START.toString());
		game.setPlayersJSON(gameData.getPlayers());
		
		GameController gameController = new GameController(game);
		// Db handling
		game.setId(gameController.getMaxGameId());
		gameController.initialLoading(game);
		gameController.saveGameToDB();
		
		gameSingleton.addGame(game);
		// go to db and save()
		gameData.setId(game.getId());
		return gameData;
	}

	// this will start a new game
	@POST
	@Path("/start")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ClientTurn startGame(GameHostingData gameHostingData) {
		Game game = gameSingleton.getGame(gameHostingData.getId());
		if (game.getStatus().equalsIgnoreCase(Game.STATUS.YET_TO_START.toString())) {
			game.setStatus(Game.STATUS.STARTED.toString());
			GameController gameController = new GameController(game);
			gameController.loadStocksFromDB();
			gameController.startTimer();
			gameController.getGameDAO().updateStatus(game);
		}
						
		// create a timer object, only first player can access this service
		ClientTurn ct = new ClientTurn();
		ct.setGameId(game.getId());
		ct.setCurrentTurn(game.getCurrentTurn());
		ct.setTotalTurns(game.getTurns());
		ct.setPlayer(game.getCreatedBy());
		ct.setGame_status(ClientTurn.GAME_STATUS.STARTED.toString());
		return ct;
	}
	
	@POST
	@Path("/stop")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void stopGame(GameHostingData data) {
		Game game = gameSingleton.getGame(data.getId());
		GameController gc = new GameController(game);
		game.setStatus(Game.STATUS.ENDED.toString());
		gc.getGameDAO().updateStatus(game);
	}
	
	@POST
	@Path("{gameid}/getStock")
	public List<Stock> getStocks(@PathParam("gameid") int gameid) {
		Game game = gameSingleton.getGame(gameid);
		return game.getStocks();
	}
	
	// this will return the updated price of stocks
	@POST
	@Path("/{gameid}/getNewPrice")	
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Stock> getNewPrices(@PathParam("gameid") int gameid, List<Stock> stocks) {
		Game game = gameSingleton.getGame(gameid);
		GameController gc = new GameController(game);
		for (Stock s : stocks)
			gc.getUpdatedStock(s);
		return stocks;
	}

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
	
	// this will check for events
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
	
	// this will make sure wether a client can request updated price info in a turn
	@GET
	@Path("/{gameid}/turn/{turn}")
	public boolean canRequest(@PathParam("gameid") int gameid, @PathParam("turn") int turn) {
		Game game = gameSingleton.getGame(gameid);
		if (game != null) {
			if (turn == game.getCurrentTurn()) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
	
	@GET
	@Path("/{gameid}/checkForPlayers")
	public String checkForPlayers(@PathParam("gameid") int gameid) {
		Game game = gameSingleton.getGame(gameid);
		if (game != null)
			return game.getPlayersJSON();
		return null;
	}
	
	@GET
	@Path("/{gameid}/isEnded")
	public boolean isEnded(@PathParam("gameid") int gameid) {
		Game game = gameSingleton.getGame(gameid);
		if(game.getStatus().equalsIgnoreCase(Game.STATUS.ENDED.toString())) {
			return true;
		}
		return false;
	}
	
	@GET
	@Path("/{gameid}/getTurn")
	public int getTurn(@PathParam("gameid") int gameid) {
		Game game = gameSingleton.getGame(gameid);
		return game.getCurrentTurn();
	}
	
	@GET
	@Path("/{gameid}/winner")
	public String getWinner(@PathParam("gameid") int gameid) {
		Game game = gameSingleton.getGame(gameid);
		return game.getWinner();
	}
	
	// this will end a game
	@POST
	@Path("/{gameid}/end")
	public void endGame() {
		gameSingleton.destroyInstance();
	}
}
