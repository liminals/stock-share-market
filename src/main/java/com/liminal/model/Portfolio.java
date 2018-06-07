package com.liminal.model;

public class Portfolio {
	private String name;
	private int qty;
	private float value;
	
	
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Portfolio [name=" + name + ", qty=" + qty + ", value=" + value + "]";
	}
}
