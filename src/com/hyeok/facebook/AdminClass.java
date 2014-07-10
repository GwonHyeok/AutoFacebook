package com.hyeok.facebook;

import java.util.Scanner;

public class AdminClass extends Thread {
	boolean isalive_timer = true;
	
	@Override
	public void run() {
		Scanner command = new Scanner(System.in);
		Scanner fm_cmd = new Scanner(System.in);
		while (true) {
			String cmd = command.next();
			//현재시간 스레드 출력 급지..
			Main.time.run = false;
			if (cmd.equals("exit")) {
				System.exit(0);
			} else if(cmd.equals("add")) {
				System.out.println("명령어를 입력하세요.");
				String str_fm_cmd = fm_cmd.next();
				System.out.println("메세지를 입력하세요.");
				String str_fm_msg = fm_cmd.next();
				AddCommand addcommand = new AddCommand("ADMIN", str_fm_cmd, str_fm_msg);
				addcommand.add();
			} else if(cmd.equals("dev")) {
				for(int i=0; i<10; i++)
				System.out.println("Author G.Hyeok");
			} else if(cmd.equals("write")) {
				System.out.println("입력하실 말을 적으세요.");
				String msg = fm_cmd.next();
				SendMessage sm = new SendMessage(Main.BASE_FACEBOOK_WRITE_URL, Main.ACCESS_TOKEN, msg);
				sm.send();
			} else if(cmd.equals("ban")) {
				Ban ban = new Ban();
				System.out.println("show, add, delete 명령어가 존재합니다.");
				System.out.println("명령어를 입력하세요.");
				String fm_msg = fm_cmd.next();
				if(fm_msg.equals("show")) {
					ban.ShowBanUser();
				} else if(fm_msg.equals("add")) {
					System.out.println("추가할 아이디를 입력하세요.");
					String id = fm_cmd.next();
					ban.AddBanUser(id);
				} else if(fm_msg.equals("delete")) {
					ban.ShowBanUser();
					System.out.println("제거할 아이디를 입력하세요.");
					String id = fm_cmd.next();
					ban.DeleteBanUser(id);
				} else {
					System.out.println("명령어가 존재하지 않습니다.");
				}
			} else if(cmd.equals("timer")) {
				isalive_timer = isalive_timer ? false:true;
			} else if(cmd.equals("help")) {
				System.out.println("exit: 프로그램 종료.\n"+
									"add: 명령어 추가.\n"+
									"write: 페이스북에 글올리기.\n"+
									"ban: 유저 밴.\n"+
									"timer: 현재시간 표시 토글.\n");
			}
			//관리자 명령어에 의한 멈춤이 있는지 확인 하고 아닐경우 
			//현재시간 스레드 출력 허용.
			if(isalive_timer) {
			Main.time.run = true;
			}
		}
	}
}
