package com.liminal.model;

public class Event {
	private int id;
	private int stockId;
	private String name;
	private String type;
	private int duration;
	private int value;
	private String sector;
	
	public enum Type {
		STOCK, SECTOR
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return this.id;
	}
	public int getStockId() {
		return stockId;
	}
	public void setStockId(int stockId) {
		this.stockId = stockId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	@Override
	public String toString() {
		return "Event [stockId=" + stockId + ", name=" + name + ", type=" + type + ", duration=" + duration + ", value="
				+ value + ", sector=" + sector + "]";
	}
	
	
}
