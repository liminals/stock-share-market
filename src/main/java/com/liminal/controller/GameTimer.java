package com.liminal.controller;

import java.util.Timer;
import java.util.TimerTask;

import com.liminal.dao.GameSingleton;
import com.liminal.model.Game;

public class GameTimer {
	
	private Timer timer;
	private long timeout = 1000 * 30;
	private GameController gameController;
	private Game game;
	
	public GameTimer(Game game) {
		this.game = game;
		this.timer = new Timer(String.valueOf("Game: " + game.getId() + ": Timer"));
		this.gameController = new GameController(game);
	}
	
	// backend server timer
	private TimerTask updateTurnTask = new TimerTask() {
		@Override
		public void run() {
			// current turn
			int currentTurn = game.getCurrentTurn();
			gameController.generateEvent(game.getEventStream(), currentTurn);
			
			//update the turn
			game.setCurrentTurn(currentTurn + 1);
			
			if (game.getCurrentTurn() > game.getTurns()) {
				System.out.println("[" + game.getId() + "]" + "[ENDED]" + game.getCurrentTurn());
				game.setStatus(Game.STATUS.ENDED.toString());
				gameController.getGameDAO().updateStatus(game);
				cancel();
			} else {
				System.out.println("[" + game.getId() + "]" + "[NEW][TURN]" + game.getCurrentTurn());
				gameController.updateStocksPrice(game.getStocks());
				gameController.chooseWinner();
				gameController.getGameDAO().updateCurrentTurn(game);
			}
		}
	};
	
	public void startTimer() {
		System.out.println("[" + game.getId() + "]" + "[GAME][STARTED]");
		timer.scheduleAtFixedRate(updateTurnTask, 1000 * 30, timeout);
	}
}
