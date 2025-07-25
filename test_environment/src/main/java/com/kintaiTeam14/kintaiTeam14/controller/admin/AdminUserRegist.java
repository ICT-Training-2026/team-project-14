package com.kintaiTeam14.kintaiTeam14.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kintaiTeam14.kintaiTeam14.form.UserRegistForm;
import com.kintaiTeam14.kintaiTeam14.service.admin.UserRegistService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class AdminUserRegist {
	
	private final UserRegistService service;

	@PostMapping("admin/user-regist-exe")
	public String userRegist(@ModelAttribute UserRegistForm f,RedirectAttributes ra) {
		
		String transition;
		
		if(service.userRegist(f)) {
			String popup="以下の内容で新規登録しました\n"
					+ "社員名："+f.getName()
					+ "\n所属コード："+f.getDepartmentId()
					+ "\n社員番号："+f.getEmployeeId();
			ra.addFlashAttribute("msg", popup);
			transition="redirect:/admin";
		}
		else {
			ra.addFlashAttribute("msg", "この社員番号（"+f.getEmployeeId()+"）は既に登録されています");
			transition="redirect:/admin/user-regist";
		}

		
		return transition;
	}
	
}
