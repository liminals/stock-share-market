package com.liminal.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.liminal.model.Player;

public class PlayerDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public PlayerDAO() {
		conn = DBConnection.connDB();
	}
	
	public int getMaxId() {
		String sql = "select max(id) from player";
		int playerId = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				playerId = rs.getInt(1);
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return ++playerId;
	}
	
	public boolean checkUsername(Player player) {
		String sql = "select * from player where username=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, player.getUsername());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public boolean checkPassword(Player player) {
		String sql = "select * from player where username=? and password=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, player.getUsername());
			pstmt.setString(2, player.getPassword());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public void savePlayer(Player player) {
		String sql = "insert into player(id, username, password) values(?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, player.getId());
			pstmt.setString(2, player.getUsername());
			pstmt.setString(3, player.getPassword());
			pstmt.executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	// used as login
	public void updateStatus(Player player) {
		String sql;
		sql = "update player set status=? where id=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, player.getStatus());
			pstmt.setInt(2, player.getId());
			pstmt.executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void updateGameStatus(Player player) {
		String sql;
		sql = "update player set game_status=? where id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, player.getGame_status());
			pstmt.setInt(2, player.getId());
			pstmt.executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void updateGame(Player player) {
		String sql;
		sql = "update player set gameid=? where id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, player.getGameid());
			pstmt.setInt(2, player.getId());
			pstmt.executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	
	public int getPlayerId(Player player) {
		String sql;
		sql = "select * from player where username=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, player.getUsername());
			rs = pstmt.executeQuery();
			if (rs.next())
				return rs.getInt("id");
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return 0;
	}
}
