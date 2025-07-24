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
		
		if(service.userRegist(f)) {
			ra.addFlashAttribute("msg", "ユーザーを新規登録しました");
		}
		else {
			ra.addFlashAttribute("msg", "すでに登録されているIDです");
		}

		
		return "redirect:/admin";
	}
	
}
