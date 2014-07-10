package com.hyeok.facebook;

public class HelpCommand {
	public static String Help() {
		String help=null;
		help = "현재 명령어는\n";
		help += "!추가 명령어:(명령어), 메세지:(메세지)\n";
		help += "!날씨 (지역)\n";
		help += "!안녕\n";
		help += "!짖어\n";
		help += "!메롱\n";
		help += "!판소리\n";
		help += "!술\n";
		help += "!그만해\n";
		help += "!훌라댄스\n";
		help += "!공부\n";
		help += "!도움말\n";
		help += "가 존재합니다.";
		return help;
	}
}
