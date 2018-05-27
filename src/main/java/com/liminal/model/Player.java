package com.liminal.model;

public class Player {
	private int id;
	private String username;
	private String password;
	private int gameid;
	private float current_balance;
	private String status;
	public enum STATUS {
		LOGED_IN, LOGED_OUT
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
	@Override
	public String toString() {
		return "Player [id=" + id + ", username=" + username + ", password=" + password + ", gameid=" + gameid
				+ ", current_balance=" + current_balance + "]";
	}
	
}
