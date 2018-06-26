package com.liminal.controller;

import com.liminal.dao.PlayerDAO;
import com.liminal.model.Player;

public class PlayerController {
	
	private Player player;
	private PlayerDAO playerDAO;
	
	public PlayerController(Player player) {
		this.player = player;
		this.playerDAO = new PlayerDAO();
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	// if username exists id set to 0, else save the player to db
	public void registerPlayer(){
		if (playerDAO.checkUsername(player)) {
			// if user exists with same username
			player.setId(0);
		} else {
			// else save player
			player.setId(playerDAO.getMaxId());
			playerDAO.savePlayer(player);
		}
	}
	
	public void loginPlayer() {
		boolean exists = playerDAO.checkUsername(player); 
		if (exists) {
			if (playerDAO.checkPassword(player)) {
				// if password matches
				player.setId(playerDAO.getPlayerId(player));
				player.setStatus(Player.STATUS.LOGED_IN.toString());
				playerDAO.updateStatus(player);
			} else {
				// if password not matches
				player.setId(0);
			}
		} else {
			player.setId(-1);
		}
	}
	
	public void logoutPlayer() {
		player.setStatus(Player.STATUS.LOGED_OUT.toString());
		playerDAO.updateStatus(player);
	}
}
