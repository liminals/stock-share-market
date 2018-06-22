package com.liminal.model;

public class GameJoinData {
	private int gameId;
	private String playerName;
	private String status;
	private String status_message;
	public enum STATUS {
		JOINED, ACCEPTED, REJECTED, REQUESTING, WAITING_FOR_START, GAME_STARTED
	}
	public enum MESSAGE {
		PLAYER_WITH_SAME_NAME_EXISTS, GAME_NOT_AVAILABLE
	}
	
	public String getStatus_message() {
		return status_message;
	}
	public void setStatus_message(String status_message) {
		this.status_message = status_message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	
}
