package com.kintaiTeam14.kintaiTeam14.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class shinseiController {

	@PostMapping("/nenkyu")
	public String nenkyu() {
		return "user/nenkyu";
	}
	
	@PostMapping("/hurikyu")
	public String hurikyu() {
		return "user/hurikyu";
	}
}
