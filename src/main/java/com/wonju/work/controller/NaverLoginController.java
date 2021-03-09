package com.wonju.work.controller;

import java.io.PrintWriter;
import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.wonju.work.dto.UserDTO;
import com.wonju.work.util.NaverLoginUtil;


@Controller
public class NaverLoginController {
	
	
	@RequestMapping("/naver_login")
	public ModelAndView naverloginCallback(HttpServletRequest request, HttpServletResponse response, @RequestParam String code, @RequestParam String state, HttpSession session) 
	throws Exception{
		
		System.out.println("callback 시작");
		
		ModelAndView mv = new ModelAndView();
		
		// response를 위한 정의
		PrintWriter writer = response.getWriter();
		
		
		OAuth2AccessToken oauthToken;
		oauthToken = NaverLoginUtil.getAccessToken(session, code, state);
		
		//1. 로그인 사용자 정보를 읽어온다
		String apiResult = NaverLoginUtil.getUserProfile(oauthToken);
		
		//2. String형식인 apiResult를 json형태로 바꿈
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(apiResult);
		org.json.simple.JSONObject jsonObj = (org.json.simple.JSONObject) obj;
		//3. 데이터 파싱
		//Top레벨 단계 _response 파싱
		org.json.simple.JSONObject response_obj = (org.json.simple.JSONObject)jsonObj.get("response");
		
		
		// 네이버에서 주는 고유 ID
		String naverIfId = (String) response_obj.get("id");
		// 네이버에서 설정된 사용자 이름
		String naverName = (String) response_obj.get("name");
		// 네이버에서 설정된 사용자 별명
		String naverNickname = (String) response_obj.get("nickname");
		// 네이버에서 설정된 이메일
		String naverEmail = (String) response_obj.get("email");
		// 네이버에서 설정된 사용자 프로필 사진
		String naverProfileImage = (String) response_obj.get("profile_image");
		

		UserDTO dto = new UserDTO();
		dto.setName(naverName);
		
		session.setAttribute("dto", dto);//로그인!!!
		
		mv.addObject("nickname", naverName);
		mv.addObject("pic", naverProfileImage);
		
		mv.setViewName("home_login");
		
		return mv;

		
	}
}
