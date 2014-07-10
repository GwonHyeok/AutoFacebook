package com.hyeok.facebook;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class Ban {
	String mysql_url = Main.MYSQL_URL;
	String mysql_id = Main.MYSQL_ID;
	String mysql_pw = Main.MYSQL_PW;
	static Connection con;

	public Ban() {
		// 처음 Class호출시 Mysql연결 해놓기.
		try {
			con = DriverManager.getConnection(mysql_url, mysql_id, mysql_pw);
		} catch (SQLException e) {
			System.out.println("데이터 베이스 연결에 실패하였습니다.");
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
		}
	}

	public void ShowBanUser() {
		// Ban유저 ID를 보여주는 메소드.
		// Ban유저 가져오는 쿼리문.
		String sql = "SELECT `ban_id` FROM `facebookbot`.`ban`";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			System.out.println("밴 유저 ID");
			while (rs.next()) {
				System.out.println("ID : " + rs.getString(1));
			}
		} catch (SQLException e) {
			System.out.println("데이터 베이스 연결 혹은 쿼리문 실행에 실패하였습니다.");
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
		}
	}

	public void DeleteBanUser(String id) {
		// Ban유저 ID를 입력받아 제거함.
		String sql = "DELETE FROM `facebookbot`.`ban` WHERE ban_id like \""+id+"\"";
		try {
			Statement st = con.createStatement();
			st.executeUpdate(sql);
			System.out.println(id + "를 밴 ID에서 제거합니다.");
		} catch (SQLException e) {
			System.out.println("데이터 베이스 연결 혹은 쿼리문 실행에 실패하였습니다.");
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
		}
	}
	
	public void AddBanUser(String id) {
		// Ban유저 ID를 입력받아 추가합.
		String sql = "INSERT INTO `facebookbot`.`ban` (`index`, `ban_id`, `timestamp`) VALUES (NULL, '"+id+"', CURRENT_TIMESTAMP);";
		try {
			Statement st = con.createStatement();
			st.execute(sql);
			System.out.println(id+"를 밴 ID에 추가했습니다.");
		} catch (SQLException e) {
			System.out.println("데이터 베이스 연결 혹은 쿼리문 실행에 실패하였습니다.");
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
		}
		
	}
}
