package com.liminal.dao;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liminal.model.BankAccount;
import com.liminal.model.BankTransaction;
import com.liminal.model.BrokerAccount;
import com.liminal.model.BrokerTransaction;
import com.liminal.model.Portfolio;

public class BrokerDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public BrokerDAO() {
		String host = "jdbc:mysql://localhost:3306/liminal_final";
		String user = "root";
		String password = "root";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(host, user, password);
		} catch(SQLException ex) {
			ex.printStackTrace();
		} catch(ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	
	// creates a broker account
	public void createAccount(BrokerAccount account) {
		String sql = "insert into brokeraccount(name) values(?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, account.getName());			
			pstmt.executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	// returns a broker account
	public BrokerAccount getAccount(String name) {
		String sql = "select * from brokeraccount where name=?";
		BrokerAccount account;
		Gson g = new Gson();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				account = new BrokerAccount();
				account.setName(rs.getString("name"));
				
				// set portfolios
				Type listPortfolio = new TypeToken<List<Portfolio>>() {}.getType();
				String portfolio = rs.getString("portfolio");
				List<Portfolio> lsp = g.fromJson(portfolio, listPortfolio);
				account.setPortfolio(lsp);
				
				// set transactions
				Type listTransactions = new TypeToken<List<BrokerTransaction>>() {}.getType();
				String transactions = rs.getString("transactions");
				List<BrokerTransaction> lsb = g.fromJson(transactions, listTransactions);
				account.setTransactions(lsb);

				return account;
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	// returns all the 
	public void updateTransactions(BrokerAccount account) {
		String sql = "update brokeraccount set transactions=? where name=?";
		Gson g = new Gson();
		try {
			pstmt = conn.prepareStatement(sql);
			String trans = g.toJson(account.getTransactions());
			
			pstmt.setString(1, trans);
			pstmt.setString(2, account.getName());
			pstmt.executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void setAccountForGame(BrokerAccount account) {
		String sql = "update brokeraccount set portfolio=?, transactions=? where name=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, null);
			pstmt.setString(2, null);
			pstmt.setString(3, account.getName());
			pstmt.executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
}
