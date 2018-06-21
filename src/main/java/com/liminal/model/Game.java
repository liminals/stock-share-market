package com.liminal.model;

import java.util.List;

import com.liminal.controller.GameTimer;

public class Game {
	private String status;
	private GameTimer gameTimer;
	private List<Stock> stocks;
	private int turns;
	private int currentTurn;
	private Event currentEvent;
	private int id;
	private String createdBy;
	private String eventStream;
	private String marketTrend;
	private String sectorTrends;
	private String marketValue;
	private String sectorValue;
	private String playersJSON;
	public enum STATUS {
		YET_TO_START, STARTED, ENDED
	}
	
	
	public String getPlayersJSON() {
		return playersJSON;
	}
	public void setPlayersJSON(String playersJSON) {
		this.playersJSON = playersJSON;
	}
	public GameTimer getGameTimer() {
		return gameTimer;
	}
	public void setGameTimer(GameTimer gameTimer) {
		this.gameTimer = gameTimer;
	}
	public List<Stock> getStocks() {
		return stocks;
	}
	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}
	public int getTurns() {
		return turns;
	}
	public void setTurns(int turns) {
		this.turns = turns;
	}
	public int getCurrentTurn() {
		return currentTurn;
	}
	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}
	public Event getCurrentEvent() {
		return currentEvent;
	}
	public void setCurrentEvent(Event currentEvent) {
		this.currentEvent = currentEvent;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String startBy) {
		this.createdBy = startBy;
	}
	public String getEventStream() {
		return eventStream;
	}
	public void setEventStream(String eventStream) {
		this.eventStream = eventStream;
	}
	public String getMarketTrend() {
		return marketTrend;
	}
	public void setMarketTrend(String marketTrend) {
		this.marketTrend = marketTrend;
	}
	public String getSectorTrends() {
		return sectorTrends;
	}
	public void setSectorTrends(String sectorTrends) {
		this.sectorTrends = sectorTrends;
	}
	public String getMarketValue() {
		return marketValue;
	}
	public void setMarketValue(String marketValue) {
		this.marketValue = marketValue;
	}
	public String getSectorValue() {
		return sectorValue;
	}
	public void setSectorValue(String sectorValue) {
		this.sectorValue = sectorValue;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
