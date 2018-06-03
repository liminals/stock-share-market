package com.liminal.model;

// a weak entity
public class BrokerTransaction {
	private String stock;
	private String type;
	private int qty;
	private float price;
	private String status;
	public enum TYPE {
		BUY, SELL
	}
	public enum STATUS {
		PRICE_DO_NOT_MATCH, INSUFFICIENT_FUNDS 
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "BrokerTransaction [stock=" + stock + ", type=" + type + ", qty=" + qty + ", price=" + price
				+ ", status=" + status + "]";
	}
		
}
