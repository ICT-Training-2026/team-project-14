package com.kintaiTeam14.kintaiTeam14.controller.user;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserHome {
	
	@PostMapping("/syukkin_user")
	public String getStartTime(Model m) {
		LocalDateTime now = LocalDateTime.now();
		String formatted = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
		System.out.println(formatted); // 例：2024-06-13-10-23-45
		m.addAttribute("now_s",formatted);
        // ここでDB保存などの処理も可能
        return "login/index";
	}
	
	@PostMapping("/taikin_user")
	public String getEndTime(Model m) {
		LocalDateTime now = LocalDateTime.now();
		String formatted = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
		System.out.println(formatted); // 例：2024-06-13-10-23-45
		m.addAttribute("now_e",formatted);
        // DB保存
		
		
        return "login/index";
	}
	
	@GetMapping("/{employee_id}/top")
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
