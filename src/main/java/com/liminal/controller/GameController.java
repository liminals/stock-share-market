/*
 * this is used to update the stock price of the stocks
 * also will contain the mutable actions for game object
 * update and sets Trend Arrays
 * */
package com.liminal.controller;

import java.util.Random;

import com.liminal.dao.GameSingleton;
import com.liminal.model.Stock;

public class GameController {
	private Random randomGenerator;
	private GameSingleton gs;

	public void setGameSingleton(GameSingleton gs) {
		this.gs = gs;
	}

	public GameController() {
		randomGenerator = new Random();
	}

	private int getRandomTrend() {
		int min = -2;
		int max = 2;
		return randomGenerator.nextInt(max + 1 - min) + min;
	}
	
	private int getSectorTrend(String sector) {
		int value;
		if (sector.equalsIgnoreCase("Finance")) {
			value = getFinanceValue(this.gs.getTurn());
		} else if (sector.equalsIgnoreCase("Utilities")){
			value = getUtilitiesValue(this.gs.getTurn());
		} else if (sector.equalsIgnoreCase("Healthcare")){
			value = getHealthcareValue(this.gs.getTurn());
		} else {
			value = getTechnologyValue(this.gs.getTurn());
		}
		return value;
	}
	
	// this will update the price
	public Stock updatePrice(Stock s) {
		int stockValue = 0;
		int marketValue = getMarketValue(this.gs.getTurn());
		int randomValue = getRandomTrend();
		int sectorValue = getSectorTrend(s.getSector());
		stockValue = marketValue + randomValue + sectorValue;
		if (stockValue < 0)
			stockValue = 0;
		float newPrice = s.getCurrent_price() + ((s.getCurrent_price() * stockValue) / 100);
		s.setCurrent_price(newPrice);
		return s;
	}
	
	// getters for trends
	private String getMarketTrend(int turn) {
		return this.gs.getGame().getMarketTrend().get(turn);
	}
	private String getFinanceTrend(int turn) {
		return this.gs.getGame().getFinanceTrend().get(turn);
	}
	private String getUtilitiesTrend(int turn) {
		return this.gs.getGame().getUtilitiesTrend().get(turn);
	}
	private String getHealthcareTrend(int turn) {
		return this.gs.getGame().getHealthcareTrend().get(turn);
	}
	private String getTechnologyTrend(int turn) {
		return this.gs.getGame().getTechnologyTrend().get(turn);
	}
	
	//getters for trend values
	private int getMarketValue(int turn) {
		return this.gs.getGame().getMarketValue().get(turn);
	}
	private int getFinanceValue(int turn) {
		return this.gs.getGame().getFinanceValue().get(turn);
	}
	private int getUtilitiesValue(int turn) {
		return this.gs.getGame().getUtilitiesValue().get(turn);
	}
	private int getHealthcareValue(int turn) {
		return this.gs.getGame().getHealthcareValue().get(turn);
	}
	private int getTechnologyValue(int turn) {
		return this.gs.getGame().getTechnologyValue().get(turn);
	}
}
