/*
 * will create the initial game object
 * only set the initial arrays
 * */
package com.liminal.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;

import com.liminal.model.Game;
import com.liminal.model.GameJoinData;

public class GameSingleton {
	private static GameSingleton instance;
	private List<Game> games;

	private GameSingleton() {
		games = new ArrayList<>();
	}
	
	public static GameSingleton getInstance() {
		if (instance == null) {
			instance = new GameSingleton();
		}
		return instance;
	}
	
	public void destroyInstance() {
		GameSingleton.instance = null;
	}

	// add the game by initializing objects and starting it
	public void addGame(Game game) {
		this.games.add(game);
	}

	public Game getGame(int id) {
		for (Game g : this.games) {
			if (g.getId() == id) {
				return g;
			}
		}
		return null;
	}

	public List<Game> getGamesYetToStart() {
		List<Game> gamesYetToStart = new ArrayList<>();
		for (Game g : games) {
			if (g.getStatus().equalsIgnoreCase(Game.STATUS.YET_TO_START.toString())){
				Game newGame = new Game();
				newGame.setId(g.getId());
				newGame.setCreatedBy(g.getCreatedBy());
				newGame.setTurns(g.getTurns());
				newGame.setPlayersJSON(g.getPlayersJSON());
				gamesYetToStart.add(newGame);
			}
		}
		return gamesYetToStart;
	}
	
	public GameJoinData checkForJoin(GameJoinData gameJoinData) {
		boolean gameFound = false;
		boolean playerFound = false;
		for (Game game : games) {
			if (game.getId() == gameJoinData.getGameId()) {
				gameFound = true;
				JSONObject players = new JSONObject(game.getPlayersJSON());
				Iterator<?> keys = players.keys();
				int i = 1;
				while (keys.hasNext()) {
					i++;
					String key = (String)keys.next();
					String player = players.getString(key);
					if (player.equalsIgnoreCase(gameJoinData.getPlayerName())) {
						playerFound = true;
						break;
					}
				}
				if (!playerFound){
					players.put(String.valueOf(i), gameJoinData.getPlayerName());
					game.setPlayersJSON(players.toString());
					gameJoinData.setStatus(GameJoinData.STATUS.ACCEPTED.toString());
					System.out.println("Player joined!!!");
				}
				break;
			}
		}
		if(playerFound) {
			gameJoinData.setStatus(GameJoinData.STATUS.REJECTED.toString());
			gameJoinData.setStatus_message(GameJoinData.MESSAGE.PLAYER_WITH_SAME_NAME_EXISTS.toString());
		}
		if(!gameFound) {
			gameJoinData.setStatus(GameJoinData.STATUS.REJECTED.toString());
			gameJoinData.setStatus_message(GameJoinData.MESSAGE.GAME_NOT_AVAILABLE.toString());
		}
		return gameJoinData;
	}
}
