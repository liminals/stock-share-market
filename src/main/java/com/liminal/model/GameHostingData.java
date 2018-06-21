package com.liminal.model;

public class GameHostingData {
	private int id;
	private int turns;
	private String createdBy;
	private String players;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTurns() {
		return turns;
	}
	public void setTurns(int turns) {
		this.turns = turns;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getPlayers() {
		return players;
	}
	public void setPlayers(String players) {
		this.players = players;
	}
	@Override
	public String toString() {
		return "GameHostingData [id=" + id + ", turns=" + turns + ", createdBy=" + createdBy + ", players=" + players
				+ "]";
	}
}
