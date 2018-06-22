package com.liminal.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.liminal.model.Game;

public class GameDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
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
	
	public int getMaxId() {
		String sql = "select max(id) from game";
		int gameid = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				gameid = rs.getInt(1);
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return ++gameid;
	}
	
	public void saveGame(Game game) {
		String sql = "insert into game(id, currentTurn, status, createdBy, eventStream, marketTrend, sectorTrends, marketValue, sectorValues, turns) values(?,?,?,?,?,?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, game.getId());
			pstmt.setInt(2, game.getCurrentTurn());
			pstmt.setString(3, game.getStatus());
			pstmt.setString(4, game.getCreatedBy());
			pstmt.setString(5, game.getEventStream());
			pstmt.setString(6, game.getMarketTrend());
			pstmt.setString(7, game.getSectorTrends());
			System.out.println(game.getSectorTrends().length());
			pstmt.setString(8, game.getMarketValue());
			pstmt.setString(9, game.getSectorValue());
			pstmt.setInt(10, game.getTurns());
			pstmt.executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
}
