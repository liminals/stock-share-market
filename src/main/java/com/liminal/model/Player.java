package com.liminal.model;

public class Player {
	private int id;
	private String username;
	private String password;
	private int gameid;				// only when joined,hosts a game
	private float current_balance;	// only when joined,hosts a game
	private String status;			
	private String game_status;		// only when joined,hosts a game
	public enum STATUS {
		LOGED_IN, LOGED_OUT
	}
	public enum GAME_STATUS {
		JOINED, HOST, NOT_JOINED
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getGameid() {
		return gameid;
	}
	public void setGameid(int gameid) {
		this.gameid = gameid;
	}
	public float getCurrent_balance() {
		return current_balance;
	}
	public void setCurrent_balance(float current_balance) {
		this.current_balance = current_balance;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGame_status() {
		return game_status;
	}
	public void setGame_status(String game_status) {
		this.game_status = game_status;
	}
	@Override
	public String toString() {
		return "Player [id=" + id + ", username=" + username + ", password=" + password + ", gameid=" + gameid
				+ ", current_balance=" + current_balance + ", status=" + status + ", game_status=" + game_status + "]";
	}	
}
