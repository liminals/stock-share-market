/*
 * this is used to update the stock price of the stocks
 * also will contain the mutable actions for game object
 * update and sets Trend Arrays
 * */
package com.liminal.controller;

import java.util.List;
import java.util.Random;

import com.liminal.dao.GameSingleton;
import com.liminal.dao.StockDAO;
import com.liminal.model.Event;
import com.liminal.model.Stock;

public class GameController {
	private Random randomGenerator;
	private GameSingleton gs;
	private StockDAO stockDAO;

	public void setGameSingleton(GameSingleton gs) {
		this.gs = gs;
	}

	public GameController() {
		randomGenerator = new Random();
		stockDAO = new StockDAO();
	}
	
	private int getRandomTrend() {
		int min = -2;
		int max = 2;
		return randomGenerator.nextInt(max + 1 - min) + min;
	}
	
	private int getSectorTrend(String sector) {
		int value;
		if (sector.equalsIgnoreCase("Finance")) {
			value = getFinanceValue(this.gs.getGame().getCurrentTurn());
		} else if (sector.equalsIgnoreCase("Utilities")){
			value = getUtilitiesValue(this.gs.getGame().getCurrentTurn());
		} else if (sector.equalsIgnoreCase("Healthcare")){
			value = getHealthcareValue(this.gs.getGame().getCurrentTurn());
		} else {
			value = getTechnologyValue(this.gs.getGame().getCurrentTurn());
		}
		return value;
	}
	
	private int getEventScore(Stock s) {
		Event currentEvent = this.gs.getGame().getCurrentEvent();
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
		int marketValue = getMarketValue(this.gs.getGame().getCurrentTurn());
		int randomValue = getRandomTrend();
		int sectorValue = getSectorTrend(s.getSector());
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
		List<Stock> stocks = this.gs.getGame().getStocks();
		for (Stock st : stocks) {
			if (st.getId() == s.getId()) {
				st.setCurrent_price(s.getCurrent_price());
			}
		}
	}
	
	public Stock getUpdatedStock(Stock s) {
		List<Stock> stocks = this.gs.getGame().getStocks();
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
	
	// this will generate the event and produce the next bits in the event stream
	public void generateEvent(List<Boolean> stream, int currentTurn) {
		if (stream.size() > currentTurn) {
			boolean eventExists = stream.get(currentTurn);
			Event event;
			if (eventExists && gs.getGame().getCurrentEvent() == null) {
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
				this.gs.getGame().setCurrentEvent(event);
				for (int i = 0; i < event.getDuration() - 1; i++) {
					stream.add(currentTurn + i + 1, true);
				}
			} else if (eventExists && gs.getGame().getCurrentEvent() != null) {
				int duration = this.gs.getGame().getCurrentEvent().getDuration();
				this.gs.getGame().getCurrentEvent().setDuration(--duration);
			}
		} else {
			this.gs.getGame().setCurrentEvent(null);
			updateEventStreamAfterEvent(stream, currentTurn);
		}
	}
	
	// this will populate the rest of the array
	public void updateEventStreamAfterEvent(List<Boolean> stream, int currentTurn) {
		for (int i = 0; i < 10; i++) {
			stream.add(false);
		}
		stream.add(true);
	}
	
	public void loadStocksFromDB() {
		this.gs.getGame().setStocks(stockDAO.getAll());
	}
	
	// main game timer
	public void startTimer() {
		this.gs.getGame().getGameTimer().startTimer();
	}
}
