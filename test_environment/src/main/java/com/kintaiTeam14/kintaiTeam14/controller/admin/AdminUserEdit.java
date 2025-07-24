package com.kintaiTeam14.kintaiTeam14.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kintaiTeam14.kintaiTeam14.form.UserEditForm;
import com.kintaiTeam14.kintaiTeam14.service.admin.UserEditService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminUserEdit {
	
	private final UserEditService s;

	@PostMapping("/admin/user-edit-exe")
	public String userEdit(@ModelAttribute UserEditForm f,Model model,RedirectAttributes ra) {
			s.userEdit(f);
		return "redirect:/admin";
	}
}
