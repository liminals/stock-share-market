/*
 * this is used to update the stock price of the stocks
 * also will contain the mutable actions for game object
 * update and sets Trend Arrays
 * */
package com.liminal.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.json.JSONObject;

import com.liminal.dao.BankDAO;
import com.liminal.dao.BrokerDAO;
import com.liminal.dao.GameDAO;
import com.liminal.dao.StockDAO;
import com.liminal.model.Event;
import com.liminal.model.Game;
import com.liminal.model.Portfolio;
import com.liminal.model.Stock;

public class GameController {
	private Random randomGenerator;
	private Game game;
	private StockDAO stockDAO;
	private GameDAO gameDAO;
	private BrokerDAO brokerDAO;
	private BankDAO bankDAO;
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public Game getGame() {
		return this.game;
	}

	public GameController(Game game) {
		this.game = game;
		this.randomGenerator = new Random();
		this.stockDAO = new StockDAO();
		this.gameDAO = new GameDAO();
	}
	
	public GameDAO getGameDAO() {
		return this.gameDAO;
	}
	
	private int getRandomTrend() {
		int min = -2;
		int max = 2;
		return randomGenerator.nextInt(max + 1 - min) + min;
	}
		
	private int getEventScore(Stock s) {
		Event currentEvent = this.game.getCurrentEvent();
		if (currentEvent != null) {
			String type = currentEvent.getType();
			if (type.equals(Event.Type.SECTOR.toString()) && currentEvent.getSector().equalsIgnoreCase(s.getSector())) {
				return currentEvent.getValue();
			} else if (type.equals(Event.Type.STOCK.toString()) && currentEvent.getStockId() == s.getId()) {
				return currentEvent.getValue();
			}
		}
		return 0;
	} 
	
	// this will update the price
	// price algorithm
	// Added events
	private void updatePrice(Stock s) {
		int stockValue = 0;
		int marketValue = getMarketValue(game.getCurrentTurn());
		int randomValue = getRandomTrend();
		int sectorValue = getSectorValue(game.getCurrentTurn(), s.getSector());
		int eventValue = getEventScore(s);
		stockValue = marketValue + randomValue + sectorValue + eventValue;
		
		if (stockValue < 0)
			stockValue = 0;
		float newPrice = s.getCurrent_price() + ((s.getCurrent_price() * stockValue) / 100);
		s.setCurrent_price(newPrice);
	}
	
	// update price of stocks in each turn
	public void updateStocksPrice(List<Stock> stocks) {
		for (Stock s : stocks) {
			updatePrice(s);
			updateStockInGame(s);
		}
		gameDAO.updateStocks(game);
	}
	
	// this will update the price in game object
	private void updateStockInGame(Stock s) {
		List<Stock> stocks = game.getStocks();
		for (Stock st : stocks) {
			if (st.getId() == s.getId()) {
				st.setCurrent_price(s.getCurrent_price());
				break;
			}
		}
	}
	
	// will return new price of stocks to client
	public void getUpdatedStock(Stock s) {
		List<Stock> stocks = game.getStocks();
		for (Stock st : stocks) {
			if (st.getId() == s.getId()) {
				s.setCurrent_price(st.getCurrent_price());
				break;
			}
		}
	}
	
	//getters for trend values
	private int getMarketValue(int turn) {
		JSONObject marketValues = new JSONObject(game.getMarketValue());
		return marketValues.getInt(String.valueOf(turn));
	}
	
	private int getSectorValue(int turn, String sector) {
		JSONObject sectorValues = new JSONObject(game.getSectorValue());
		JSONObject sectorValue = sectorValues.getJSONObject(sector);
		return sectorValue.getInt(String.valueOf(turn));
	}
		
	public void generateEvent(String stream, int currentTurn) {
		JSONObject streamJSON = new JSONObject(stream);
		if (streamJSON.length() >= currentTurn) {
			boolean eventExists = streamJSON.getBoolean(String.valueOf(currentTurn));
			Event event;
			if (eventExists && game.getCurrentEvent() == null) {
				event = new Event();
				float prob = randomGenerator.nextFloat();
				if (prob <= 0.33) {
					event.setType(Event.Type.SECTOR.toString());
					event.setSector(stockDAO.getSectors().get(randomGenerator.nextInt(4)));
					event.setDuration(randomGenerator.nextInt(5 + 1 - 2) + 2);
					prob = randomGenerator.nextFloat();
					if (prob < 0.5) {
						event.setName("BOOM");
						event.setValue(randomGenerator.nextInt(5 + 1 - 1) + 1);
					} else {
						event.setName("BUST");
						int dur = randomGenerator.nextInt(5 + 1 - 1) + 1;
						// event.setValue(randomGenerator.nextInt(-5 + 1 -(-1)) + -1);
						dur = -dur;
						event.setValue(dur);
					}
				} else {
					event.setType(Event.Type.STOCK.toString());
					event.setDuration(randomGenerator.nextInt(7 + 1 - 1) + 1);
					event.setStockId(stockDAO.getStocks().get(randomGenerator.nextInt(12)));
					prob = randomGenerator.nextFloat();
					if (prob > 0.5) {
						event.setName("PROFIT_WARNING");
						event.setValue(randomGenerator.nextInt(3 + 1 - 2) + 2);
					} else if (prob > 0.25) {
						event.setName("TAKE_OVER");
						int dur = randomGenerator.nextInt(5 + 1 - 1) + 1;
						dur = -dur;
						event.setValue(dur);
					} else {
						event.setName("SCANDAL");
						int dur = randomGenerator.nextInt(6 + 1 - 3) + 3;
						dur = -dur;
						event.setValue(dur);
					}
				}
				game.setCurrentEvent(event);
				gameDAO.updateEvent(game);
				for (int i = 0; i < event.getDuration() - 1; i++) {
					streamJSON.put(String.valueOf(currentTurn + i + 1), true);
				}
				stream = streamJSON.toString();
				gameDAO.updateEventStream((game));
			} else if (eventExists && game.getCurrentEvent() != null) {
				int duration = game.getCurrentEvent().getDuration();
				game.getCurrentEvent().setDuration(--duration);
				gameDAO.updateEvent(game);
			}
		} else {
			game.setCurrentEvent(null);
			gameDAO.updateEvent(game);
			stream = updateEventStreamAfterEvent(streamJSON.toString(), currentTurn);
		}
		game.setEventStream(stream);
		gameDAO.updateEventStream((game));
	}
	
	// this will populate the rest of the array
	public String updateEventStreamAfterEvent(String stream, int currentTurn) {
		JSONObject streamJSON = new JSONObject(stream);
		int length = streamJSON.length();
		for (int i = length; i < length + 10; i++) {
			streamJSON.put(String.valueOf(i), false);
		}
		streamJSON.put(String.valueOf(length + 10), true);
		return streamJSON.toString();
	}
	
	
	// main game timer
	public void startTimer() {
		game.getGameTimer().startTimer();
	}
	
	// ///////////////////////////////////////     DAOs
	public void loadStocksFromDB() {
		game.setStocks(stockDAO.getAll());
		gameDAO.updateStocks(game);
	}
	
	public int getMaxGameId() {
		return gameDAO.getMaxId();
	}
	
	public void saveGameToDB() {
		gameDAO.saveGame(game);
	}
	
	public void updatePlayersInDB() {
		gameDAO.updatePlayerJoin(game);
	}
	///////////////// game construction
	// constructs global array objects
	public void initialLoading(Game game) {
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

	
	////////////////////// game winner calculations
	/////// winner = bankbalance + portfolio value >>
	
	// get latest price of a stock
	private float getLatestStockValue(String name, List<Stock> stocks) {
		for (Stock s : stocks) {
			if (s.getName().equalsIgnoreCase(name))
				return s.getCurrent_price();
		}
		return 0f;
	}
	
	// calculate player assets
	private float getPlayerAssets(String name) {
		brokerDAO = new BrokerDAO();
		bankDAO = new BankDAO();
		float assets;
		float bank_balance = bankDAO.getBalanceByName(name);
		List<Portfolio> lsPort = brokerDAO.getPortfolio(name);
		
		float portfolioworth = 0;
		if (lsPort != null) {
			for (Portfolio p : lsPort) {
				float latestStockPrice = getLatestStockValue(p.getName(), game.getStocks());
				if (latestStockPrice == 0) {
					portfolioworth += p.getValue();
				} else {
					portfolioworth += p.getQty() * latestStockPrice;
				}
				
			}
		}
		assets = bank_balance + portfolioworth;
		return assets;
	}
	
	// choose and set the winner happens in each turn
	public void chooseWinner() {
		String playersJson = this.game.getPlayersJSON();
		JSONObject jo = new JSONObject(playersJson);
		List<String> players = new ArrayList<>();
		int count = jo.length();
		for (int i = 1; i <= count; i++) {
			String name = jo.getString(String.valueOf(i));
			if (!name.equalsIgnoreCase("AI")) 
				players.add(name);
		}
		String current_winner = game.getWinner();
		float max_assests = 0;
		for (String name : players) {
			float assests = getPlayerAssets(name);
			if (assests > max_assests) {
				current_winner = name;
			}
		}
		game.setWinner(current_winner);
		gameDAO.updateWinner(game.getId(), game.getWinner());
	}
	
}
