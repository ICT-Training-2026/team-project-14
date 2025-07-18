package com.kintaiTeam14.kintaiTeam14.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PasswordChangeController {
	
	@PostMapping("/passChange_form_user")
	public String passwordChange() {
		
		return "/login/index";
	}
}
