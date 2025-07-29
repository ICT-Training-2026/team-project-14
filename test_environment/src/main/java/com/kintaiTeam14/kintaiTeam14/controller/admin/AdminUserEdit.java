package com.kintaiTeam14.kintaiTeam14.controller.admin;

import java.time.format.DateTimeFormatter;

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
		
		switch(s.userEdit(f)) {
		case 0:
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
			String formatted = f.getActivateDate().format(formatter);
			s.editSchedule(f);
			String msg="編集予定を登録しました。\n実行日："+formatted;
			ra.addFlashAttribute("msg", msg);
			break;
		case 1:
			ra.addFlashAttribute("msg", "存在しない部署IDです");
			break;
		case 2:
			ra.addFlashAttribute("msg", "編集前と内容が同じだったため更新されませんでした");
			break;
		}
		
		return "redirect:/admin/User-management";
	}
}
