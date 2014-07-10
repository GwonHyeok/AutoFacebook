package com.hyeok.facebook;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main {

	/*
	 * ACCESS_TOKEN = 직접 Facebook APP 만들어서 발급
	 * BASE_FACEBOOK_WRITE_URL = 수정하지 않기.
	 * UPDATE_TIME_SECOND = 업데이트 주기 초단위.
	 * MYSQL_URL = 	MYSQL 주소
	 * MYSQL_ID  = 	MYSQL 아이디
	 * MYSQL_PW  = 	MYSQL 비밀번호
	 */
	
	static String ACCESS_TOKEN = "CAALjBqAZAvP0BAFkIgXdZCT8gUZCPnqn4I3MjFmZCAM45cmxrzK5B6UqewZBg4WWh3xIcQFuTqOiRSndq0yS2oZACB1ZBKQaJiMLp7LsdCoWl6utem6HSBaKIKOYJ9d7TotjXfI8xrc8mXhwPAL7ymMbSYqNAvFLYm6vh5PekjVbYFKTwSjT99ZBrEEVOZA1ApX8ZD";
	static String BASE_FACEBOOK_WRITE_URL = "https://graph.facebook.com/me/feed";
	static int UPDATE_TIME_SECOND = 5; // 업데이트 주기
	public static String MYSQL_URL = "jdbc:mysql://kh4975.iptime.org"; //Mysql 주소
	public static String MYSQL_ID = "root"; //Mysql 아이디
	public static String MYSQL_PW = ""; //Mysql 비밀번호
	public static gettime time = new gettime();
	
	public static void main(String args[]) {		
		//if(args != null) Command(args); //처음 실행시 명령어 확인하기.
		CheckString checkstring = new CheckString();
		AdminClass adminclass = new AdminClass();
		checkstring.start();
		time.start();
		adminclass.start();
	}

	public static void Command(String args[]) {
		if(args[0].equals("-h")) {
			System.out.println("-u Mysql주소 -i Mysql아이디 -p Mysql비밀번호");
			System.exit(0);
		}
		
		String command = "";
		for (int i=0; i < args.length; i++) {
			command += args[i];
			command += " ";
			System.out.println(command);
		}
	}
	
	public static String getWeather(String recentmessage) {
		@SuppressWarnings("unused")
		String day,year,hour,month,city,desc,icon,stn_id,ta ;
	    String msg = null;
		day = year = hour = month = city = desc = icon = stn_id = ta = null;
		boolean state = true;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		int t_stn_id = GetCity.getcity_id(recentmessage);
		int index;
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			String url = ("http://www.kma.go.kr/XML/weather/sfc_web_map.xml");
			Document doc = db.parse(url);
			doc.getDocumentElement().normalize();
			Element order = doc.getDocumentElement();
			System.out.println ("Root element: " + doc.getDocumentElement().getNodeName());
			NodeList titems = order.getElementsByTagName("weather");
			NamedNodeMap tAttrs = titems.item(0).getAttributes();
		       for (int j = 0; j < tAttrs.getLength(); j++){
		          Node attr = tAttrs.item(j);
		          System.out.println((attr.getNodeName() + " = " + attr.getNodeValue() + " "));
		         if(attr.getNodeName().equals("day")) day = attr.getNodeValue();
		         	else if (attr.getNodeName().equals("hour")) hour = attr.getNodeValue();
		         	else if (attr.getNodeName().equals("month")) month = attr.getNodeValue();
		         	else if (attr.getNodeName().equals("year")) year = attr.getNodeValue();
		       }	
		       
			NodeList items = order.getElementsByTagName("local");
			for(index = 0; index < items.getLength(); index++) {
				Node item = items.item(index);
				NamedNodeMap Attrs = item.getAttributes();
			       for (int j = 0; j < Attrs.getLength(); j++){
			          Node attr = Attrs.item(j);
				         if (attr.getNodeName().equals("stn_id") && Integer.parseInt(attr.getNodeValue()) == t_stn_id)  {
				        	 stn_id = attr.getNodeValue();
				        	 state = false;
				        	 break;
				         }
			       }
			       if(!state) {
			    	   state = true;
			    	   break;
			       }
			}
			Node item = items.item(index);
			Node text = item.getFirstChild();
			String ItemName = text.getNodeValue();
			city = ItemName;
			System.out.println(ItemName);
			NamedNodeMap Attrs = item.getAttributes();
		       for (int j = 0; j < Attrs.getLength(); j++){
		          Node attr = Attrs.item(j);
		          System.out.println((attr.getNodeName() + " = " + attr.getNodeValue() + " "));
		          if(attr.getNodeName().equals("desc")) desc = attr.getNodeValue();
			         else if (attr.getNodeName().equals("icon")) icon = attr.getNodeValue();
			         else if (attr.getNodeName().equals("stn_id")) stn_id = attr.getNodeValue();
			         else if (attr.getNodeName().equals("ta")) ta = attr.getNodeValue();
		       }
		      try {
		       msg = year+"년 "+month+"월 "+day+"일 "+hour+"시\n"+city+"의 온도는 "+ta+"도 이고 날씨 상태는 "+desc+"입니다.";
		      } catch(NullPointerException e) {
		    	  msg = "날씨정보를 가져오는데 오류가 발생하였습니다.";
		      }
		      
		      System.out.println(msg);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return msg;
	}
}

class CheckString extends Thread {
	public void run() {
		while(true) {
		try {
			HttpsURLConnection conn = (HttpsURLConnection)new URL(Main.BASE_FACEBOOK_WRITE_URL+"?access_token="+Main.ACCESS_TOKEN).openConnection();
			InputStream is = conn.getInputStream();
	        String inputStreamString = new Scanner(is,"UTF-8").useDelimiter("\\A").next();
	        JSONParser parser = new JSONParser();
			Object obj = parser.parse(inputStreamString);
			JSONObject job = (JSONObject) obj;
			JSONArray fmessage = (JSONArray) job.get("data");
			JSONObject message = (JSONObject) fmessage.get(0);
			String recentmsg = (String) message.get("message");
			JSONObject fromdata = (JSONObject) message.get("from");
			System.out.println("최근 담벼락 글");
			System.out.println("회원 ID : "+fromdata.get("id")); // 회원 id
			System.out.println("회원 이름 : "+fromdata.get("name")); // 회원 이름
			System.out.println("메세지 : ");
			System.out.println(recentmsg);
			System.out.println("\n");
			if(recentmsg.contains("!날씨")) {
				System.out.println("!날씨가 입력되었습니다.");
				SendMessage sm = new SendMessage(Main.BASE_FACEBOOK_WRITE_URL, Main.ACCESS_TOKEN, Main.getWeather(recentmsg));
				sm.send();
			} else if(recentmsg.contains("!안녕")) {
				System.out.println("!안녕이 입력되었습니다.");
				SendMessage sm = new SendMessage(Main.BASE_FACEBOOK_WRITE_URL, Main.ACCESS_TOKEN, (String)fromdata.get("name")+"님 안녕하세요.\n저는 권혁을 가장한 봇 입니다.");
				sm.send();
			} else if(recentmsg.contains("!짖어")) {
				System.out.println("!짖어가 입력되었습니다.");
				SendMessage sm = new SendMessage(Main.BASE_FACEBOOK_WRITE_URL, Main.ACCESS_TOKEN, (String)fromdata.get("name")+"님 잘 들으세요 왈왈 왈왈 그르르릉 그릉 왈왈.\n저는 권혁을 가장한 봇 입니다.");
				sm.send();
			} else if(recentmsg.contains("!판소리")) {
				System.out.println("!판소리가 입력되었습니다.");
				SendMessage sm = new SendMessage(Main.BASE_FACEBOOK_WRITE_URL, Main.ACCESS_TOKEN, (String)fromdata.get("name")+"님 잘 들으세요 덩기덕 쿵 더러러러 쿵 기덕 쿵덕 얼 쑤 ~.\n저는 권혁을 가장한 봇 입니다.");
				sm.send();
			} else if(recentmsg.contains("!메롱")) {
				System.out.println("!메롱이 입력되었습니다.");
				SendMessage sm = new SendMessage(Main.BASE_FACEBOOK_WRITE_URL, Main.ACCESS_TOKEN, (String)fromdata.get("name")+"님 그냥 메롱 가져가세요...\n저는 권혁을 가장한 봇 입니다.");
				sm.send();
			} else if(recentmsg.contains("!술")) {
				System.out.println("!술이 입력되었습니다.");
				SendMessage sm = new SendMessage(Main.BASE_FACEBOOK_WRITE_URL, Main.ACCESS_TOKEN, (String)fromdata.get("name")+"님 마셔라 마셔라 마셔라 술이들어간다 쭉쭉쭉쭉..\n"+(String)fromdata.get("name")+"님께서 술을 사주신다고요?? 저야 감사하죠\n"+"저는 권혁을 가장한 봇 입니다.");
				sm.send();
			} else if(recentmsg.contains("!그만해")) {
				System.out.println("!그만해 입력되었습니다.");
				SendMessage sm = new SendMessage(Main.BASE_FACEBOOK_WRITE_URL, Main.ACCESS_TOKEN, (String)fromdata.get("name")+"님이 봇을 종료 하셨습니다.");
				sm.send();
				System.exit(0);
			} else if(recentmsg.contains("!훌라댄스")) {
				System.out.println("!훌라댄스가 입력되었습니다.");
				SendMessage sm = new SendMessage(Main.BASE_FACEBOOK_WRITE_URL, Main.ACCESS_TOKEN, (String)fromdata.get("name")+"\n훌라훌라 훌라훌라 훌라댄스 춤을 춥시다~.\n저는 권혁을 가장한 봇 입니다.");
				sm.send();
			} else if(recentmsg.contains("!공부")) {
				System.out.println("!공부가 입력되었습니다.");
				SendMessage sm = new SendMessage(Main.BASE_FACEBOOK_WRITE_URL, Main.ACCESS_TOKEN, (String)fromdata.get("name")+"\n공부? 공부가 뭐죠?? 먹는건가 냠냠냠 아이 맛있어.\n저는 권혁을 가장한 봇 입니다.");
				sm.send();
			} else if(recentmsg.contains("!도움말")) {
				System.out.println("!도움말");
				SendMessage sm = new SendMessage(Main.BASE_FACEBOOK_WRITE_URL, Main.ACCESS_TOKEN, HelpCommand.Help());
				sm.send();
			} else if(recentmsg.contains("!추가") && !recentmsg.contains("명령어를 추가하시려면")) {
				System.out.println("!추가가 입력되었습니다.");
				String tmp_command = recentmsg.split("명령어:")[1].split("메세지:")[0];
				String command = tmp_command.substring(0, tmp_command.lastIndexOf(","));
				String msg = recentmsg.split("메세지:")[1];
				AddCommand addcommand = new AddCommand((String)fromdata.get("name"), command, msg);
				SendMessage sm;
				if (addcommand.add())
					sm = new SendMessage(Main.BASE_FACEBOOK_WRITE_URL, Main.ACCESS_TOKEN, command+" 명령어를 성공적으로 추가하였습니다.");
				else 
					sm = new SendMessage(Main.BASE_FACEBOOK_WRITE_URL, Main.ACCESS_TOKEN, "명령어 추가에 실패하였습니다.\n서버 관리자에게 문의하세요.");
				sm.send();
			} else if(String.valueOf(recentmsg.charAt(0)).equals("!") && !recentmsg.contains("명령어를 추가하시려면")){
				//서버에서 명령어 가져옴.
				System.out.println("하드코딩된 명령어가 없습니다. 서버를 확인합니다.");
				 GetCommand getcommand = new GetCommand(recentmsg);
				 String msg = getcommand.get();
				 msg = msg.replaceAll("_NAME_", (String)fromdata.get("name"));
				 SendMessage sm = new SendMessage(Main.BASE_FACEBOOK_WRITE_URL, Main.ACCESS_TOKEN, msg);
				 sm.send();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			/*
			 * e.printStackTrace();
			 * 메세지가 없을때.
			 */
		} finally {
			try {
				Thread.sleep(Main.UPDATE_TIME_SECOND*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	} 
}

class SendMessage {
	private String url;
	private String token;
	private String message;

	public SendMessage(String url, String token, String message) {
		this.url = url;
		this.token = token;
		this.message = message;
	}

	
	@SuppressWarnings("deprecation")
	public void send() {
		String httpsURL = url;
		String query = "access_token=" + token + "&message=";
		query += URLEncoder.encode(message);
		URL myurl;
		try {
			myurl = new URL(httpsURL);
			HttpsURLConnection con = (HttpsURLConnection) myurl
					.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-length",
					String.valueOf(query.length()));
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			con.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)");
			con.setDoOutput(true);
			con.setDoInput(true);
			DataOutputStream output = new DataOutputStream(
					con.getOutputStream());
			output.writeBytes(query.toString());
			output.close();
			DataInputStream input = new DataInputStream(con.getInputStream());
			for (int c = input.read(); c != -1; c = input.read())
				System.out.print((char) c);
			input.close();
			System.out.println("Resp Code:" + con.getResponseCode());
			System.out.println("Resp Message:" + con.getResponseMessage());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class gettime extends Thread {
	public boolean run = true;
	
	public void run() {
		while (true) {
		Calendar cl = Calendar.getInstance();
		SimpleDateFormat form = new SimpleDateFormat("MM-dd/hh:mm:ss");
		if(run) {
		System.out.println("현제 시간:"+form.format(cl.getTime()));
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		}
	}
}