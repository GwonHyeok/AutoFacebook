package com.hyeok.facebook;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author gwonhyeok
 * 이 부분은 명령어를 DB에 넣는 부분
 * 명령어를 추가한 사람 (adder_name) 
 * 명령어 (command)
 * 메세지 (msg)
 * 
 */

public class AddCommand {
	private String adder_name;
	private String command;
	private String msg;
	private String mysql_url = Main.MYSQL_URL;
	private String mysql_id = Main.MYSQL_ID;
	private String mysql_pw = Main.MYSQL_PW;
	
	public AddCommand(String adder_name, String command, String msg) {
		this.adder_name = adder_name;
		this.command = command;
		this.msg = msg;
	}

	public boolean add() {
		String sql = "INSERT INTO `facebookbot`.`data` (`index`, `adder_name`, `command`, `msg`, `timestamp`) VALUES ( NULL, '"+adder_name+"', '"+command+"', '"+msg+"', CURRENT_TIMESTAMP);";
		java.sql.Connection con = null;
		try {
			con = DriverManager.getConnection(mysql_url, mysql_id, mysql_pw);
		Statement st = null;
		st = con.createStatement();
		// 명령어를 등록하는 쿼리문을 실행.
		st.execute(sql);
		System.out.println("명령어 등록 성공!");
		System.out.println(adder_name+"님이"+command+":"+msg+"명령어를 추가하셨습니다.");
		return true;
		} catch (SQLException e) {
			System.out.println("명령어 등록에 실패하였습니다.");
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			return false;
		}
	}
}
