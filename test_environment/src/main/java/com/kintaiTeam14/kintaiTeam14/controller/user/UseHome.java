package com.kintaiTeam14.kintaiTeam14.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UseHome {
	  @GetMapping("/{userId}/top")
	    public String getMethodName() {
	        return "login/index";
	    }

}
