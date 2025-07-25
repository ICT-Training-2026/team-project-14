package com.kintaiTeam14.kintaiTeam14.controller.admin;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kintaiTeam14.kintaiTeam14.form.UserEditForm;
import com.kintaiTeam14.kintaiTeam14.service.admin.ScheduleService;
import com.kintaiTeam14.kintaiTeam14.service.admin.UserEditService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminUserEdit {
	
	private final UserEditService s;
	private final ScheduleService scheduleService;

	@PostMapping("/admin/user-edit-exe")
	public String userEdit(@ModelAttribute UserEditForm f,Model model,RedirectAttributes ra) {
		
		if(s.userEdit(f)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH時mm分");
			String formatted = f.getActivateDate().format(formatter);
			String msg="編集予定を登録しました。\n実行日："+formatted;
			scheduleService.scheduleTask(f.getActivateDate(),f);
			ra.addFlashAttribute("msg", "編集予定を登録しました\n");
		}
		else {
			ra.addFlashAttribute("msg", "編集前と内容が同じだったため更新されませんでした");
		}
		return "redirect:/admin/User-management";
	}
}
