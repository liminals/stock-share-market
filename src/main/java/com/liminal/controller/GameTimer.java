package com.liminal.controller;

import java.util.Timer;
import java.util.TimerTask;

import com.liminal.dao.GameSingleton;

public class GameTimer {
	
	private Timer timer;
	private long timeout = 1000 * 60;
	private GameSingleton gameSingleton;
	private GameController gameController;
	
	public GameTimer(GameSingleton gs) {
		this.timer = new Timer();
		this.gameSingleton = gs;
		this.gameController = new GameController();
		this.gameController.setGameSingleton(this.gameSingleton);
	}
	
	// backend server timer
	private TimerTask updateTurnTask = new TimerTask() {
		@Override
		public void run() {
			// current turn
			int currentTurn = gameSingleton.getGame().getCurrentTurn();
			gameController.generateEvent(gameSingleton.getGame().getEventStream(), currentTurn - 1);
			
			//update the turn
			gameSingleton.getGame().setCurrentTurn(currentTurn + 1);
			System.out.println("[NEW][TURN]" + gameSingleton.getGame().getCurrentTurn());
			gameController.updateStocksPrice(gameSingleton.getGame().getStocks());
			if (gameSingleton.getGame().getCurrentTurn() == gameSingleton.getGame().getTurns()) {
				System.out.println("cancelling");
				cancel();
				gameSingleton.destroyInstance();
			}
		}
	};
	
	public void startTimer() {
		timer.scheduleAtFixedRate(updateTurnTask, 1000 * 3, timeout);
	}
}
