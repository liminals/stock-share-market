package com.liminal.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.liminal.model.Stock;

public class StockDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public StockDAO() {
		String url = "jdbc:mysql://localhost:3306/liminal_final";
		String user = "root";
		String password = "root";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
		} catch(SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// initial return of all the stocks
	public List<Stock> getAll(){
		List<Stock> stocks = new ArrayList<Stock>();
		String sql = "select * from stock";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Stock s = new Stock();
				s.setId(rs.getInt(1));
				s.setSector(rs.getString(2));
				s.setName(rs.getString(3));
				s.setCurrent_price(rs.getFloat(4));
				stocks.add(s);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return stocks;
	}
	
	// T14
	// update the price of single stock
	// need to create price algorithm
	public Stock updatePrice(Stock s) {
		double newPrice = (s.getCurrent_price() * 1.5) / 3;
		s.setCurrent_price(newPrice);
		return s;
	}
}
