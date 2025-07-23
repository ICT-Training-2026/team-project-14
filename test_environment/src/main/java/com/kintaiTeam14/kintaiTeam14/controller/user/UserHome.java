package com.kintaiTeam14.kintaiTeam14.controller.user;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kintaiTeam14.kintaiTeam14.form.ChangePasswordForm;
import com.kintaiTeam14.kintaiTeam14.service.employee.AttendanceAndDepatureService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserHome {

	private final AttendanceAndDepatureService ads;
	
	//リダイレクト用
	@GetMapping("/login/index")
	public String uesrTopRedirect(@PathVariable Long employeeId) {
		return employeeId.toString()+"/top";
	}
	
	
	@PostMapping("/{employeeId}/top/syukkin_user")
	public String getStartTime(Model m,@PathVariable Long employeeId,RedirectAttributes ra) {
		
		LocalDateTime now = LocalDateTime.now();
		//DB登録
		String now_s = ads.attendance(now,employeeId);
		ra.addFlashAttribute("now", now_s);
		
	    return "redirect:/"+employeeId.toString()+"/top";
	}
	
	
	@PostMapping("/{employeeId}/top/taikin_user")
	public String getEndTime(Model m,@PathVariable Long employeeId,RedirectAttributes ra) {
		
		LocalDateTime now = LocalDateTime.now();
		//DB登録
		String now_e = ads.depature(now,employeeId);
		ra.addFlashAttribute("now", now_e);
		
	    return "redirect:/"+employeeId.toString()+"/top";
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
