package com.liminal.model;

public class Stock {
	private int id;
	private String sector;
	private String name;
	private double current_price;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCurrent_price() {
		return current_price;
	}

	public void setCurrent_price(double current_price) {
		this.current_price = current_price;
	}

	@Override
	public String toString() {
		return "Stock [id=" + id + ", sector=" + sector + ", name=" + name + ", current_price=" + current_price + "]";
	}

}
