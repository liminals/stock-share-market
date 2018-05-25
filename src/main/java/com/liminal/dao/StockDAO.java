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
				s.setSector(rs.getString(3));
				s.setName(rs.getString(2));
				s.setCurrent_price(rs.getFloat(4));
				stocks.add(s);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return stocks;
	}
	
	public Stock updatePrice(Stock s) {
		String sql = "update stock set current_price=? where id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setFloat(1, s.getCurrent_price());
			pstmt.setInt(2, s.getId());
			pstmt.executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return s;
	}
	
	public List<String> getSectors() {
		String sql = "select sector from stock";
		List<String> sectors = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				sectors.add(rs.getString(1));
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return sectors;
	}
	
	public List<Integer> getStocks() {
		String sql = "select id from stock";
		List<Integer> stocks = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				stocks.add(rs.getInt(1));
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return stocks;
	}
}
