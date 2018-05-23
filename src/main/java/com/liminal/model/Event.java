package com.liminal.model;

public class Event {
	private int id;
	private String name;
	private String type;
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return "Event [id=" + id + ", name=" + name + ", type=" + type + "]";
	}
	
}
