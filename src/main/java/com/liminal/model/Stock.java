package com.liminal.model;

public class Stock {
	private int id;
	private String sector;
	private String name;
	private float current_price;
	private String short_name;

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

	public float getCurrent_price() {
		return current_price;
	}

	public void setCurrent_price(float current_price) {
		this.current_price = current_price;
	}

	public String getShort_name() {
		return short_name;
	}

	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}

	@Override
	public String toString() {
		return "Stock [id=" + id + ", sector=" + sector + ", name=" + name + ", current_price=" + current_price
				+ ", short_name=" + short_name + "]";
	}

}
