package com.liminal.model;

public class BankTransaction {
	private int turn;
	private String name; 	// bank account identifier 
	private int gameid;
	private String type;
	private String sender;
	private String receiver;
	
	public enum TYPE {
		DEPOSIT, WITHDRAW
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getGameid() {
		return gameid;
	}

	public void setGameid(int gameid) {
		this.gameid = gameid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	@Override
	public String toString() {
		return "BankTransaction [turn=" + turn + ", name=" + name + ", gameid=" + gameid + ", type=" + type
				+ ", sender=" + sender + ", receiver=" + receiver + "]";
	}
	
}
