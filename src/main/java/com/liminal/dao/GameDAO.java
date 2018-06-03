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
import com.liminal.model.Game;
import com.liminal.model.Stock;

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
			pstmt.setString(8, game.getMarketValue());
			pstmt.setString(9, game.getSectorValue());
			pstmt.setInt(10, game.getTurns());
			pstmt.executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void updateStocks(Game game) {
		String sql;
		sql = "update game set stocks=? where id=?";
		
		Gson g = new Gson();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, g.toJson(game.getStocks()));
			pstmt.setInt(2, game.getId());
			pstmt.executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void updateEvent(Game game) {
		String sql;
		sql = "update game set event=? where id=?";
		
		Gson g = new Gson();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, g.toJson(game.getCurrentEvent()));
			pstmt.setInt(2, game.getId());
			pstmt.executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void updateEventStream(Game game) {
		String sql;
		sql = "update game set eventStream=? where id=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, game.getEventStream());
			pstmt.setInt(2, game.getId());
			pstmt.executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void updateCurrentTurn(Game game) {
		String sql;
		sql = "update game set currentTurn=? where id=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, game.getCurrentTurn());
			pstmt.setInt(2, game.getId());
			pstmt.executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void updateStatus(Game game) {
		String sql;
		sql = "update game set status=? where id=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, game.getStatus());
			pstmt.setInt(2, game.getId());
			pstmt.executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public List<Stock> getCurrentPricesofStock(int gameid) {
		String sql = "select stocks from game where id=?";
		Gson g = new Gson();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, gameid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Type listStocks = new TypeToken<List<Stock>>() {}.getType();
				String stocks = rs.getString(1);
				List<Stock> lss = g.fromJson(stocks, listStocks);
				return lss;
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
