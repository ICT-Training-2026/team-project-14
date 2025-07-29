package com.kintaiTeam14.kintaiTeam14.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kintaiTeam14.kintaiTeam14.form.UserRegistForm;
import com.kintaiTeam14.kintaiTeam14.service.admin.UserRegistService;

import lombok.RequiredArgsConstructor;
/*test*/

@Controller
@RequiredArgsConstructor
public class AdminUserRegist {
	
	private final UserRegistService service;

	@PostMapping("admin/user-regist-exe")
	public String userRegist(@ModelAttribute UserRegistForm f,RedirectAttributes ra) {
		
		String transition;

		//0:成功 1:存在しない部署 2:ID重複
		switch(service.userRegist(f)) {
		case 0:
			String popup="以下の内容で新規登録しました\n"
					+ "社員名："+f.getName()
					+ "\n所属コード："+f.getDepartmentId()
					+ "\n社員番号："+f.getEmployeeId();
			ra.addFlashAttribute("msg", popup);
			transition="redirect:/admin";
			break;
			
		case 1:
			ra.addFlashAttribute("msg", "存在しない所属コードです");
			transition="redirect:/admin/user-regist";
			break;
			
		case 2:
			ra.addFlashAttribute("msg", "この社員番号（"+f.getEmployeeId()+"）は既に登録されています");
			transition="redirect:/admin/user-regist";
			break;
			
		default:
			ra.addFlashAttribute("msg", "予期せぬエラーが発生しました");
			transition="redirect:/admin/user-regist";
		}
		
		return transition;
	}
	
}
