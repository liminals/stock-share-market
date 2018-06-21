package com.liminal.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GameDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	
	public GameDAO() {
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
}
