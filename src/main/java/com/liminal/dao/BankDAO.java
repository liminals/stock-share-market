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
		String sql = "select * from banktransaction where name=?";
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
}
