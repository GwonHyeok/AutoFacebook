package com.hyeok.facebook;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GetCommand {
	private String command;
	private String mysql_url = Main.MYSQL_URL;
	private String mysql_id = Main.MYSQL_ID;
	private String mysql_pw = Main.MYSQL_PW;

	public GetCommand(String recentmsg) {
		this.command = recentmsg.substring(1);
	}

	public String get() {
		String sql = "SELECT `command` FROM `facebookbot`.`data`";
		ArrayList<String> msgarray = new ArrayList<String>();
		java.sql.Connection con = null;
		try {
			con = DriverManager.getConnection(mysql_url, mysql_id, mysql_pw);
			Statement st = con.createStatement();
			Statement st2 = con.createStatement();
			//command컬럼에 있는 데이터를 가져옴.
			ResultSet rs = st.executeQuery(sql);
			ResultSet rs2 = null;
			while (rs.next()) {
				//명령어가 존재 하는지 확인
				if(rs.getString(1).equals(command)) {
					//명령어가 존재 한다면 그 명령어에 대응하는
					//메세지들을 가져옴
					String sql2 = "SELECT `msg` FROM `facebookbot`.`data` WHERE `command` LIKE \""+command+"\"";
					rs2 = st2.executeQuery(sql2);
				}
			}
			//명령어에 대한 메세지 데이터 값이 NULL이 아니면 Array에 메세지들을 추가함.
			if (rs2!=null) {
				while (rs2.next()) {
				msgarray.add(rs2.getString(1));
				}
			}
			// 명령어가 존재 하지 않을경우 == 명령어에 대응되는 메세지가 없음. 즉 Array에는 데이터가 없음 size=0
			// 이경우 return 으로 명령어가 없다는 메세지를 리턴함.
			if (msgarray.size() == 0) {
				String nomsg = command+"명령어가 존재하지 않습니다.\n명령어를 추가하시려면 !추가 명령어:"+command+", 메세지:내용\n이렇게 적어주세요.";
				return nomsg;
			}
			// 명령어가 존재 한다면 for문으로 예상 가능한 메세지를 보여줌.
			for(int i=0; i < msgarray.size(); i++) {
				System.out.println("예상 명령어에대한 메세지 : "+msgarray.get(i));
			}
			// Array의 크기를 가지고 랜덤한 인덱스 값을 설정.
			int size = String.valueOf(msgarray.size()).length();
			int gab = 1;
			for(int i=0; i < size; i++) {
				gab = 10 * gab; 
			}
			// 5개면 10의단위 10개면 100의단위 100개면 1000의단위 
			int index=0;
			while (true) {
				index = (int) (Math.random() * gab);
				if ( index < msgarray.size()) break;
			}
			// 위에서 생성된 랜덤한 인덱스 값의 메세지를 리턴함.
			String msg = msgarray.get(index);
			return msg;
		} catch (SQLException e) {
			System.out.println("메세지를 불러오는데 실패하였습니다.");
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
		}
		return "메지를 불러오는데 실패하였습니다.\n서버 관리자에게 연락을 해보시기 바랍니다.";
	}
}
