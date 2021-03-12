package com.wonju.work.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@Autowired
	private HttpSession session;
	
	
	@GetMapping("/")
	public String first_main() {
		
		session.setAttribute("client_id", "d0c2de2c1a07232359c7898ac6c66a85");
		session.setAttribute("redirect_uri", "http://localhost:8080/kakao_login");
		
		return "home"; //jsp 리턴
	}
}
