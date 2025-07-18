package com.generalfunction.demo.Controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserHome {
	@GetMapping("/{username}/top")
	public String getMethodName() {
		return "login/index";
	}
	
	@PostMapping("/jisseki_user")
	public String jissekikanri(){
		return "user/jissekikanri";
	}
	
	@PostMapping("/passChange_user")
	public String passwordChange() {
		return "user/passwordChange";
	}
	
	@PostMapping("/shinsei_user")
	public String kakusyusinsei() {
		return "user/shinsei";
	}

}
