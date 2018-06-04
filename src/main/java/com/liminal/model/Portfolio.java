package com.liminal.model;

public class Portfolio implements Comparable<Portfolio> {
	private int id;
	private String name;
	private float value;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
		return "Portfolio [id=" + id + ", name=" + name + ", value=" + value + "]";
	}
	@Override
	public int compareTo(Portfolio o) {
		return id - o.id;
	}	
	
}
