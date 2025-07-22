package com.kintaiTeam14.kintaiTeam14.controller.user;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.kintaiTeam14.kintaiTeam14.form.ChangePasswordForm;

@Controller
public class UserHome {

	
	@PostMapping("/{employeeId}/top/syukkin_user")
	public String getStartTime(Model m) {
		LocalDateTime now = LocalDateTime.now();
		String formatted = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
		System.out.println(formatted); // 例：2024-06-13-10-23-45
		m.addAttribute("now_s",formatted);
        // ここでDB保存などの処理も可能
        return "login/index";
	}
	
	@PostMapping("/{employeeId}/top/taikin_user")
	public String getEndTime(Model m,@PathVariable Long employeeId) {
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
	
//	@PostMapping("/{employeeId}/top/jisseki_user")
//	public String jissekiUser(
//	        @PathVariable Long employeeId,
//	        Model model) {
//	    // 必要に応じてemployeeIdをモデルに渡す
//	    model.addAttribute("employeeId", employeeId);
//
//	    // "user/jisseki" テンプレート（src/main/resources/templates/user/jisseki.html）を表示
//	    return "user/jissekikanri";
//	}
	
	@PostMapping("/{employeeId}/top/passChange_user")
	public String passwordChange(Model m,@PathVariable Long employeeId,@ModelAttribute("form") ChangePasswordForm form) {
		m.addAttribute("employeeId", employeeId);
		m.addAttribute("form", new ChangePasswordForm()); 
		return "user/passwordChange";
	}
	
	@PostMapping("/{employeeId}/top/shinsei_user")
	public String kakusyusinsei(Model m,@PathVariable Long employeeId) {
		m.addAttribute("employeeId", employeeId);

		return "user/shinsei";
	}
	
	@GetMapping("/{employeeId}/top")
	public String getMethodName(Model m,@PathVariable Long employeeId) {
		m.addAttribute("employeeId", employeeId);
	

		return "login/index";
	}

}
