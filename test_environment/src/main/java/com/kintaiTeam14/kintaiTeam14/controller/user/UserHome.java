package com.kintaiTeam14.kintaiTeam14.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserHome {
	@GetMapping("/{username}/top")
	public String getMethodName() {
		return "login/index";
	}
	
	@GetMapping("/user/jisseki_user")
	public String jissekikanri(){
		return "user/jissekikanri";
	}
	
	@GetMapping("/user/passChange_user")
	public String passwordChange() {
		return "user/passwordChange";
	}
	
	@GetMapping("/user/shinsei_user")
	public String kakusyusinsei() {
		return "user/shinsei";
	}

}
