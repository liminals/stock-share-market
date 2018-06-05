package com.liminal.model;

public class Portfolio {
	private String name;
	private float value;
	
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
		return "Portfolio [name=" + name + ", value=" + value + "]";
	}
}
