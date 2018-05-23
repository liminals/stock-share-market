package com.liminal.model;

import java.util.List;

import com.liminal.controller.GameTimer;

public class Game {
	private GameTimer gameTimer;
	private List<Stock> stocks;
	private int turns;
	private int currentTurn;
	private Event currentEvent;
	private List<String> marketTrend;
	private List<Integer> marketValue;
	private List<String> financeTrend;
	private List<Integer> financeValue;
	private List<String> utilitiesTrend;
	private List<Integer> utilitiesValue;
	private List<String> healthcareTrend;
	private List<Integer> healthcareValue;
	private List<String> technologyTrend;
	private List<Integer> technologyValue;
	private List<Boolean> eventStream;
	
	
	public List<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

	public GameTimer getGameTimer() {
		return gameTimer;
	}
	
	public Event getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(Event currentEvent) {
		this.currentEvent = currentEvent;
	}


	public void setGameTimer(GameTimer gameTimer) {
		this.gameTimer = gameTimer;
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
	public List<String> getMarketTrend() {
		return marketTrend;
	}
	public void setMarketTrend(List<String> marketTrend) {
		this.marketTrend = marketTrend;
	}
	public List<Integer> getMarketValue() {
		return marketValue;
	}
	public void setMarketValue(List<Integer> marketValue) {
		this.marketValue = marketValue;
	}
	public List<String> getFinanceTrend() {
		return financeTrend;
	}
	public void setFinanceTrend(List<String> financeTrend) {
		this.financeTrend = financeTrend;
	}
	public List<Integer> getFinanceValue() {
		return financeValue;
	}
	public void setFinanceValue(List<Integer> financeValue) {
		this.financeValue = financeValue;
	}
	public List<String> getUtilitiesTrend() {
		return utilitiesTrend;
	}
	public void setUtilitiesTrend(List<String> utilitiesTrend) {
		this.utilitiesTrend = utilitiesTrend;
	}
	public List<Integer> getUtilitiesValue() {
		return utilitiesValue;
	}
	public void setUtilitiesValue(List<Integer> utilitiesValue) {
		this.utilitiesValue = utilitiesValue;
	}
	public List<String> getHealthcareTrend() {
		return healthcareTrend;
	}
	public void setHealthcareTrend(List<String> healthcareTrend) {
		this.healthcareTrend = healthcareTrend;
	}
	public List<Integer> getHealthcareValue() {
		return healthcareValue;
	}
	public void setHealthcareValue(List<Integer> healthcareValue) {
		this.healthcareValue = healthcareValue;
	}
	public List<String> getTechnologyTrend() {
		return technologyTrend;
	}
	public void setTechnologyTrend(List<String> technologyTrend) {
		this.technologyTrend = technologyTrend;
	}
	public List<Integer> getTechnologyValue() {
		return technologyValue;
	}
	public void setTechnologyValue(List<Integer> technologyValue) {
		this.technologyValue = technologyValue;
	}
	public List<Boolean> getEventStream() {
		return eventStream;
	}
	public void setEventStream(List<Boolean> eventStream) {
		this.eventStream = eventStream;
	}
	
}
