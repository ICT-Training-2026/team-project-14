package com.kintaiTeam14.kintaiTeam14.controller.user;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UseHome {
	  @GetMapping("/{userId}/top")
	    public String getMethodName(@PathVariable Long userId,Model model) {
				model.addAttribute("userId",userId);
	        return "login/index";
	    }

}
