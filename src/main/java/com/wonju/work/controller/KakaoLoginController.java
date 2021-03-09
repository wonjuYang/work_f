package com.wonju.work.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wonju.work.dto.UserDTO;

@Controller
public class KakaoLoginController {
	
		@Autowired
		private HttpSession session;
		
		

		@RequestMapping("/kakao_login")
		public ModelAndView kakaoLogin(String code) {
			//카카오서버에서 인증코드를 전달해 주는 곳!
			ModelAndView mv = new ModelAndView();

			//받은 코드를 가지고 다시 토큰을 받기 위한 작업 - POST방식
			String access_Token = "";
			String refresh_Token = "";
			String reqURL = "https://kauth.kakao.com/oauth/token";
			
			try {
				URL url = new URL(reqURL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				
				//POST방식으로 요청하기 위해 setDoOutput을 true로 지정
				conn.setRequestMethod("POST");//요청방식 설정
				conn.setDoOutput(true);
				
				//인자 4개를 만들어서 카카오 서버로 보낸다.
				// grant_type, client_id, redirect_uri, code
				StringBuffer sb = new StringBuffer();
				sb.append("grant_type=authorization_code&client_id=d0c2de2c1a07232359c7898ac6c66a85");
				sb.append("&redirect_uri=http://localhost:8080/kakao_login");
				sb.append("&code="+code);
				
				//전달하고자 하는 파라미터들을 보낼 스트림 준비
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
				bw.write(sb.toString());//요청을 POST방식으로 보낸다.
				bw.flush();//스트림 비우기
				
				//결과코드가 200이면 성공!!!
				int res_code = conn.getResponseCode();
				//System.out.println("RES_CODE:"+res_code);
				
				if(res_code == 200) { //요청이 성공시
					//요청을 통해 얻은 JSON타입의 결과메세지를 읽어온다.
					BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					
					String line = "";
					StringBuffer result = new StringBuffer();
					while((line = br.readLine()) != null) {
						result.append(line);
					}
					
					br.close();
					bw.close();
					
					//받은 결과 확인
					//System.out.println("result"+result.toString());
					
					//JSON파싱 처리 
					JSONParser j_par = new JSONParser();
					Object obj = j_par.parse(result.toString());
					
					//자바에서 편하게 사용할 수 있도록 JSON객체로 변환하자!
					JSONObject j_obj = (JSONObject) obj;
					
					access_Token = (String) j_obj.get("access_token");
					refresh_Token = (String) j_obj.get("refresh_token");
					
					//사용자 정보를 얻기 위해 토큰을 이용한 서버 요청 시작
					String header = "Bearer "+access_Token;
					String apiURL = "https://kapi.kakao.com/v1/api/talk/profile";
					
					URL url2 = new URL(apiURL);
					HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
					
					//POST방식 설정
					conn2.setRequestMethod("POST");
					conn2.setDoOutput(true);
					
					conn2.setRequestProperty("Authorization", header);
					
					res_code = conn2.getResponseCode();
					if(res_code == HttpURLConnection.HTTP_OK) {
						//정상적으로 사용자 정보를 요청했다면
						BufferedReader brd = new BufferedReader(
								new InputStreamReader(conn2.getInputStream()));
						StringBuffer sBuff = new StringBuffer();
						String str = null;
						while((str = brd.readLine()) != null) {
							sBuff.append(str); //카카오 서버에서 전달되는 모든 값들이
										//sBuff에 누적된다. (JSON)
						}
						
						obj = j_par.parse(sBuff.toString());// JSON인식
						
						//JSON으로 인식된 정보를 다시 JSON객체로 형변환한다.
						j_obj = (JSONObject) obj;
						
						//System.out.println("j_obj"+j_obj);


						String name = (String) j_obj.get("nickName");
						String t_img = (String) j_obj.get("thumbnailURL");
						
						
						UserDTO dto = new UserDTO();
						dto.setName(name);
						
						session.setAttribute("dto", dto);//로그인!!!
						session.setAttribute("access", access_Token);
						
						mv.addObject("nickname", name);
						mv.addObject("pic", t_img);
					}
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			mv.setViewName("home_login");
			
			
			return mv;
		}
		

		
		@RequestMapping("/logout")
		public String access(HttpSession session) throws IOException {
			
			System.out.println("로그아웃");
			
			String reqURL = "https://kapi.kakao.com/v1/user/logout";
		    try {
		        URL url = new URL(reqURL);
		        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		        conn.setRequestMethod("POST");
		        conn.setRequestProperty("Authorization", "Bearer " + session.getAttribute("access"));
		        
		        int responseCode = conn.getResponseCode();
		        System.out.println("responseCode : " + responseCode);
		        
		        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        
		        String result = "";
		        String line = "";
		        
		        while ((line = br.readLine()) != null) {
		            result += line;
		        }
		        System.out.println(result);
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
			
			//String result = conn.HttpPostConnection("https://kapi.kakao.com/v1/user/logout", map).toString();
			//System.out.println(result);
			
			session.removeAttribute("dto");
			session.removeAttribute("access");
			
			return "home";
		}


}