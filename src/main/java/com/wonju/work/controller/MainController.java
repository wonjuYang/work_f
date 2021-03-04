package com.wonju.work.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String first_main() {
		return "home"; //jsp 리턴
	}
}
