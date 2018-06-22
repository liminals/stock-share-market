package com.liminal.model;

public class ClientTurn {
	private int gameId;
	private int totalTurns;
	private int currentTurn;
	private String type;
	private String player;
	private String game_status;
	public enum TYPE {
		CLIENT, HOST
	}
	public enum GAME_STATUS {
		YET_TO_START, STARTED, ENDED
	}
	
	
	public String getGame_status() {
		return game_status;
	}
	public void setGame_status(String game_status) {
		this.game_status = game_status;
	}
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public int getTotalTurns() {
		return totalTurns;
	}
	public void setTotalTurns(int totalTurns) {
		this.totalTurns = totalTurns;
	}
	public int getCurrentTurn() {
		return currentTurn;
	}
	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}
}
