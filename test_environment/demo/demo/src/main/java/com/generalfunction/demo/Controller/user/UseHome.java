package com.generalfunction.demo.Controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UseHome {
	  @GetMapping("/{username}/top")
	    public String getMethodName() {
	        return "login/index";
	    }

}
