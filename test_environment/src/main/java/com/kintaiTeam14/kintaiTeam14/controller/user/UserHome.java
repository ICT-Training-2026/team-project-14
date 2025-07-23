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
import com.kintaiTeam14.kintaiTeam14.service.employee.AttendanceAndDepatureService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserHome {

	private final AttendanceAndDepatureService ads;
	
	@PostMapping("/{employeeId}/top/syukkin_user")
	public String getStartTime(Model m,@PathVariable Long employeeId) {
		LocalDateTime now = LocalDateTime.now();
		String formatted = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
		m.addAttribute("now_s",formatted);
		
        //db登録
		ads.attendance(now,employeeId);
        return "login/index";
	}
	
	@PostMapping("/{employeeId}/top/taikin_user")
	public String getEndTime(Model m,@PathVariable Long employeeId) {
		LocalDateTime now = LocalDateTime.now();
		String formatted = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
		m.addAttribute("now_e",formatted);
		
        // DB登録
		ads.depature(now, employeeId);
		
        return "login/index";
	}
	
//	@PostMapping("/{employeeId}/top/jisseki_user")
//	public String jissekiUser(
//	        @PathVariable Long employeeId,
//	        Model model) {
//	    // 必要に応じてemployeeIdをモデルに渡す
//	    model.addAttribute("employeeId", employeeId);
//
//	    return "user/jisseki_user";
//	}
	
	@PostMapping("/{employeeId}/top/passChange_user")
	public String passwordChange(Model m,@PathVariable Long employeeId,@ModelAttribute("form") ChangePasswordForm form) {
		m.addAttribute("employeeId", employeeId);
		m.addAttribute("form", new ChangePasswordForm()); 
		
		return "user/passwordChange";
	}
	
	@GetMapping("/{employeeId}/top/passChange_user")
	public String passwordChangeGet(Model m,@PathVariable Long employeeId,@ModelAttribute("form") ChangePasswordForm form) {
		m.addAttribute("employeeId", employeeId);
		m.addAttribute("form", new ChangePasswordForm()); 
		
		return "user/passwordChange";
	}
	
	
	@PostMapping("/{employeeId}/top/shinsei_user")
	public String kakusyusinsei(Model m,@PathVariable Long employeeId) {
		m.addAttribute("employeeId", employeeId);

		return "user/shinsei";
	}
	
	
	
	@GetMapping("/{employeeId}/top/shinsei_user")
	public String kakusyusinseiget(Model m,@PathVariable Long employeeId) {
		m.addAttribute("employeeId", employeeId);

		return "user/shinsei";
	}
	
	@GetMapping("/{employeeId}/top")
	public String getMethodName(Model m,@PathVariable Long employeeId) {
		m.addAttribute("employeeId", employeeId);
	

		return "login/index";
	}
	
	
	
	
	
	
	

}
