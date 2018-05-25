/*
 * this is used to update the stock price of the stocks
 * also will contain the mutable actions for game object
 * update and sets Trend Arrays
 * */
package com.liminal.controller;

import java.util.List;
import java.util.Random;

import org.json.JSONObject;

import com.liminal.dao.StockDAO;
import com.liminal.model.Event;
import com.liminal.model.Game;
import com.liminal.model.Stock;

public class GameController {
	private Random randomGenerator;
	private Game game;
	private StockDAO stockDAO;
	
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
	private Stock updatePrice(Stock s) {
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
		saveNewPriceToDatabase(s);
		return s;
	}
	
	public void updateStocksPrice(List<Stock> stocks) {
		for (Stock s : stocks) {
			updatePrice(s);
			updateStockInGame(s);
		}
	}
	
	// this will update the price in game object
	private void updateStockInGame(Stock s) {
		List<Stock> stocks = game.getStocks();
		for (Stock st : stocks) {
			if (st.getId() == s.getId()) {
				st.setCurrent_price(s.getCurrent_price());
			}
		}
	}
	
	public Stock getUpdatedStock(Stock s) {
		List<Stock> stocks = game.getStocks();
		for (Stock st : stocks) {
			if (st.getId() == s.getId()) {
				s.setCurrent_price(st.getCurrent_price());
				return s;
			}
		}
		return null;
	}
	
	private void saveNewPriceToDatabase(Stock s) {
		stockDAO.updatePrice(s);
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
				for (int i = 0; i < event.getDuration() - 1; i++) {
					streamJSON.put(String.valueOf(currentTurn + i + 1), true);
				}
				stream = streamJSON.toString();
			} else if (eventExists && game.getCurrentEvent() != null) {
				int duration = game.getCurrentEvent().getDuration();
				game.getCurrentEvent().setDuration(--duration);
			}
		} else {
			game.setCurrentEvent(null);
			stream = updateEventStreamAfterEvent(streamJSON.toString(), currentTurn);
		}
		game.setEventStream(stream);
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
	
	public void loadStocksFromDB() {
		game.setStocks(stockDAO.getAll());
	}
	
	// main game timer
	public void startTimer() {
		game.getGameTimer().startTimer();
	}
}
