/*
 * will create the initial game object
 * only set the initial arrays
 * */
package com.liminal.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;

import com.liminal.controller.GameTimer;
import com.liminal.model.Game;

public class GameSingleton {
	private static GameSingleton instance;
	private Random randomGenerator;
	private List<Game> games;

	private GameSingleton() {
		games = new ArrayList<>();
		randomGenerator = new Random();
	}
	
	// add the game by initializing objects and starting it
	public void addGame(Game game) {
		initialLoading(game);
		this.games.add(game);
		game.getGameTimer().startTimer();
	}
	
	public Game getGame(int id) {
		for (Game g : this.games) {
			if (g.getId() == id) {
				return g;
			}
		}
		return null;
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

	// constructs global array objects
	private void initialLoading(Game game) {
		game.setMarketTrend(getMarketTrendJSON(game.getTurns()));
		game.setSectorTrends(getSectorTrendsJSON(game.getTurns()));
		game.setMarketValue(setMarketValuesJSON(game.getMarketTrend(), game.getMarketValue(), game.getTurns()));
		game.setSectorValue(setSectorValuesJSON(game.getSectorTrends(), game.getSectorValue(), game.getTurns()));
		game.setEventStream(makeEventStream());
	}
	
	// construct the global trend arrays
	private String getMarketTrendJSON(int turns) {
		JSONObject ob = new JSONObject();
		for (int i = 1; i <= turns; i++) {
			float prob = randomGenerator.nextFloat();
			if (prob >= 0.5) {
				ob.put(String.valueOf(i), "doesn't change");
			} else if (prob > 0.25) {
				ob.put(String.valueOf(i), "decrease");
			} else {
				ob.put(String.valueOf(i), "increase");
			}
		}
		return ob.toString();
	}
 	
	private String getSectorTrendsJSON(int turns) {
		JSONObject main = new JSONObject();
		JSONObject finance = new JSONObject();
		JSONObject utilities = new JSONObject();
		JSONObject healthcare = new JSONObject();
		JSONObject technology = new JSONObject();
				
		for (int i = 1; i <= turns; i++) {
			float prob = randomGenerator.nextFloat();
			if (prob >= 0.5) {
				finance.put(String.valueOf(i), "doesn't change");
			} else if (prob > 0.25) {
				finance.put(String.valueOf(i), "decrease");
			} else {
				finance.put(String.valueOf(i), "increase");
			}
		}
		for (int i = 1; i <= turns; i++) {
			float prob = randomGenerator.nextFloat();
			if (prob >= 0.5) {
				utilities.put(String.valueOf(i), "doesn't change");
			} else if (prob > 0.25) {
				utilities.put(String.valueOf(i), "decrease");
			} else {
				utilities.put(String.valueOf(i), "increase");
			}
		}
		for (int i = 1; i <= turns; i++) {
			float prob = randomGenerator.nextFloat();
			if (prob >= 0.5) {
				healthcare.put(String.valueOf(i), "doesn't change");
			} else if (prob > 0.25) {
				healthcare.put(String.valueOf(i), "decrease");
			} else {
				healthcare.put(String.valueOf(i), "increase");
			}
		}
		for (int i = 1; i <= turns; i++) {
			float prob = randomGenerator.nextFloat();
			if (prob >= 0.5) {
				technology.put(String.valueOf(i), "doesn't change");
			} else if (prob > 0.25) {
				technology.put(String.valueOf(i), "decrease");
			} else {
				technology.put(String.valueOf(i), "increase");
			}
		}
		main.put("finance", finance);
		main.put("utilities", utilities);
		main.put("healthcare", healthcare);
		main.put("technology", technology);
		return main.toString();
	}
	
	private String setMarketValuesJSON(String trends, String value, int turns) {
		JSONObject trendJSON = new JSONObject(trends);
		JSONObject valueJSON = new JSONObject();
		
		int min = -3;
		int max = 3;
		int initValue = randomGenerator.nextInt(max + 1 -min) + min;
		valueJSON.put(String.valueOf(1), initValue);
		for (int i = 2; i <= turns; i++){
			String trend = trendJSON.getString(String.valueOf(i));
			int prevValue = valueJSON.getInt(String.valueOf(i-1));
			if (trend.equalsIgnoreCase("doesn't change")) {
				valueJSON.put(String.valueOf(i), prevValue);
			} else if (trend.equalsIgnoreCase("decrease")) {
				prevValue = prevValue - 1;
				if (prevValue < -3)
					valueJSON.put(String.valueOf(i), -3);
				else
					valueJSON.put(String.valueOf(i), prevValue);
			} else {
				prevValue = prevValue + 1;
				if (prevValue > 3)
					valueJSON.put(String.valueOf(i), 3);
				else
					valueJSON.put(String.valueOf(i), prevValue);
			}
		}
		return valueJSON.toString();
	}
	
	private String setSectorValuesJSON(String trends, String value, int turns) {
		JSONObject main = new JSONObject(trends);
		JSONObject financeTrend = main.getJSONObject("finance");
		JSONObject utilitiesTrend = main.getJSONObject("utilities");
		JSONObject healthcareTrend = main.getJSONObject("healthcare");
		JSONObject technologyTrend = main.getJSONObject("technology");
		
		JSONObject sectorValuesJSON = new JSONObject();
		JSONObject financeValue = new JSONObject();
		JSONObject utilitiesValue = new JSONObject();
		JSONObject healthcareValue = new JSONObject();
		JSONObject technologyValue = new JSONObject();
		
		int min = -3;
		int max = 3;
		
		getSingleSectorValueJSON(financeTrend, financeValue, min, max, turns);
		getSingleSectorValueJSON(utilitiesTrend, utilitiesValue, min, max, turns);
		getSingleSectorValueJSON(healthcareTrend, healthcareValue, min, max, turns);
		getSingleSectorValueJSON(technologyTrend, technologyValue, min, max, turns);
		
		sectorValuesJSON.put("finance", financeValue);
		sectorValuesJSON.put("utilities", utilitiesValue);
		sectorValuesJSON.put("healthcare", healthcareValue);
		sectorValuesJSON.put("technology", technologyValue);
		
		return sectorValuesJSON.toString();
	}
	
	private void getSingleSectorValueJSON(JSONObject trends, JSONObject values, int min, int max, int turns) {
		int initValue = randomGenerator.nextInt(max + 1 -min) + min;
		values.put(String.valueOf(1), initValue);
		for (int i = 2; i <= turns; i++){
			String trend = trends.getString(String.valueOf(i));
			int prevValue = values.getInt(String.valueOf(i-1));
			if (trend.equalsIgnoreCase("doesn't change")) {
				values.put(String.valueOf(i), prevValue);
			} else if (trend.equalsIgnoreCase("decrease")) {
				prevValue = prevValue - 1;
				if (prevValue < -3)
					values.put(String.valueOf(i), -3);
				else
					values.put(String.valueOf(i), prevValue);
			} else {
				prevValue = prevValue + 1;
				if (prevValue > 3)
					values.put(String.valueOf(i), 3);
				else
					values.put(String.valueOf(i), prevValue);
			}
		}
	}
	
	private String makeEventStream() {
		JSONObject eventStream = new JSONObject();
		for (int i = 1; i <= 10; i++) {
			eventStream.put(String.valueOf(i), false);
		}
		eventStream.put(String.valueOf(11), true);
		// stream.add(true);
		return eventStream.toString();
	}

}
