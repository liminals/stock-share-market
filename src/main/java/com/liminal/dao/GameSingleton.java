/*
 * will create the initial game object
 * only set the initial arrays
 * */
package com.liminal.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.liminal.controller.GameController;
import com.liminal.controller.GameTimer;
import com.liminal.model.Game;

public class GameSingleton {
	private static GameSingleton instance;
	private Random randomGenerator;
	private Game game;

	private GameSingleton() {
		game = new Game();
		game.setTurns(35);
		game.setCurrentTurn(1);
		randomGenerator = new Random();
		initialLoading(game.getTurns());
	}
	
	public void setGameTimer() {
		this.game.setGameTimer(new GameTimer(GameSingleton.instance));
	}
	
	public void startGameTimer() {
		this.game.getGameTimer().startTimer();
	}

	public static GameSingleton getInstance() {
		if (instance == null) {
			instance = new GameSingleton();
		}
		return instance;
	}
	
	public void destroyInstance() {
		this.instance = null;
	}
	public Game getGame() {
		return this.game;
	}

	// constructs global array objects
	private void initialLoading(int turns) {
		game.setMarketTrend(getTrendList(game.getMarketTrend(), turns));
		game.setFinanceTrend(getTrendList(game.getFinanceTrend(), turns));
		game.setUtilitiesTrend(getTrendList(game.getUtilitiesTrend(), turns));
		game.setHealthcareTrend(getTrendList(game.getHealthcareTrend(), turns));
		game.setTechnologyTrend(getTrendList(game.getTechnologyTrend(), turns));
		
		game.setMarketValue(setTrendValues(game.getMarketValue(), game.getMarketTrend()));
		game.setFinanceValue(setTrendValues(game.getFinanceValue(), game.getFinanceTrend()));
		game.setUtilitiesValue(setTrendValues(game.getUtilitiesValue(), game.getUtilitiesTrend()));
		game.setHealthcareValue(setTrendValues(game.getHealthcareValue(), game.getHealthcareTrend()));
		game.setTechnologyValue(setTrendValues(game.getTechnologyValue(), game.getTechnologyTrend()));
		
		game.setEventStream(makeEventStream(game.getEventStream()));
	}
	
	// only set the initial trend value
	private List<Integer> setTrendValues(List<Integer> valueArray, List<String> trendArray) {
		valueArray = new ArrayList<>();
		int min = -3;
		int max = 3;
		int initValue = randomGenerator.nextInt(max + 1 -min) + min;
		valueArray.add(0, initValue);
		for (int i = 1; i < this.game.getTurns(); i++) {
			String trend = trendArray.get(i);
			int prevValue = valueArray.get(i-1);
			if (trend.equalsIgnoreCase("doesn't change")) {
				valueArray.add(i, prevValue);
			} else if (trend.equalsIgnoreCase("decrease")) {
				prevValue = prevValue - 1;
				if (prevValue < -3)
					valueArray.add(i, -3);
				else
					valueArray.add(i, prevValue);
			} else {
				prevValue = prevValue + 1;
				if (prevValue > 3)
					valueArray.add(i, 3);
				else
					valueArray.add(i, prevValue);
			}
		}
		return valueArray;
	}
	
	// construct the global trend arrays
	private List<String> getTrendList(List<String> trendArray, int turns) {
		trendArray = new ArrayList<>();
		for (int i = 0; i < turns; i++) {
			float prob = randomGenerator.nextFloat();
			if (prob >= 0.5) {
				trendArray.add(i, "doesn't change");
			} else if (prob > 0.25) {
				trendArray.add(i, "decrease");
			} else {
				trendArray.add(i, "increase");
			}
		}
		return trendArray;
	}
 	
	// return initial event stream
	private List<Boolean> makeEventStream(List<Boolean> stream) {
		stream = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			stream.add(false);
		}
		stream.add(true);
		return stream;
	}
	
	// run this after an event ended
	private List<Boolean> updateEventStream(List<Boolean> stream) {
		int currentTurn = this.game.getCurrentTurn() - 1;
		for (int i = currentTurn; i < currentTurn + 9; i++) {
			stream.add(false);
		}
		stream.add(true);
		return stream;
	}
}
