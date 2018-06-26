package com.liminal.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.liminal.controller.PlayerController;
import com.liminal.model.Player;

@Path("/player")
public class PlayerService {
	
	@POST
	@Path("/register")
	public Player registerPlayer(Player player) {
		PlayerController controller = new PlayerController(player);
		controller.registerPlayer();
		return player;
	}
	
	@POST
	@Path("/login")
	public Player loginPlayer(Player player) {
		PlayerController controller = new PlayerController(player);
		controller.loginPlayer();
		return player;
	}
	
	@POST
	@Path("/logout")
	public void logoutPlayer(Player player) {
		PlayerController controller = new PlayerController(player);
		controller.logoutPlayer();
	}
}
