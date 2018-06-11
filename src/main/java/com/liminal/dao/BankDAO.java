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

public class BankDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public BankDAO() {
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
	
	public boolean checkAccount(String name) {
		String sql = "select * from bankaccount where name=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public void createAccount(BankAccount account) {
		String sql = "insert into bankaccount(name, current_balance) values(?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, account.getName());
			pstmt.setFloat(2, account.getCurrent_balance());			
			pstmt.executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public BankAccount getAccount(String name) {
		String sql = "select * from bankaccount where name=?";
		BankAccount account;
		Gson g = new Gson();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				account = new BankAccount();
				account.setName(rs.getString("name"));
				account.setCurrent_balance(rs.getFloat("current_balance"));
				
				Type listBankTransactions = new TypeToken<List<BankTransaction>>() {}.getType();
				String transactions = rs.getString("transactions");
				List<BankTransaction> lsts = g.fromJson(transactions, listBankTransactions);
				account.setTransactions(lsts);
				return account;
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public void getBalance(BankAccount account) {
		String sql = "select current_balance from bankaccount where name=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, account.getName());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				float balance = rs.getFloat(1);
				account.setCurrent_balance(balance);
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public float getBalanceByName(String name) {
		String sql = "select current_balance from bankaccount where name=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				float balance = rs.getFloat(1);
				return balance;
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return 0f;
	}
	
	public void setAccountForGame(BankAccount account) {
		String sql = "update bankaccount set current_balance=?, transactions=? where name=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setFloat(1, 1000.0000f);
			pstmt.setString(2, null);
			pstmt.setString(3, account.getName());
			pstmt.executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void updateAccountAfterTransaction(BankAccount account) {
		String sql = "update bankaccount set current_balance=?, transactions=? where name=?";
		Gson g = new Gson();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setFloat(1, account.getCurrent_balance());
			pstmt.setString(2, g.toJson(account.getTransactions()));
			pstmt.setString(3, account.getName());
			pstmt.executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
}
