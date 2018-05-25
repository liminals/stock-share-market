package com.liminal.model;

public class ClientTurn {
	private int gameId;
	private int totalTurns;
	private int currentTurn;
	
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
