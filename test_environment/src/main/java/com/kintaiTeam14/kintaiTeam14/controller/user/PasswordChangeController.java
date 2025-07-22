package com.kintaiTeam14.kintaiTeam14.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.kintaiTeam14.kintaiTeam14.form.ChangePasswordForm;
import com.kintaiTeam14.kintaiTeam14.service.employee.EmployeeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PasswordChangeController {
	 private final EmployeeService employeeService;
//	@PostMapping("/passChange_form_user")
//	public String passwordChange() {
//		
//		return "/login/index";
//	}
	
	
	@GetMapping("/{employeeId}/top/passChange_form_user")
	public String ShowpasswordSetting(@PathVariable("employeeId")Long employeeId, Model model) {
		model.addAttribute("employeeId", employeeId);
	
		    model.addAttribute("form", new ChangePasswordForm()); 
		return"/{employeeId}/top/passChange_form_user";
	}
	@PostMapping("/{employeeId}/top/passChange_form_user")
	public String passwordSetting(
	        @Valid @ModelAttribute("form") ChangePasswordForm form,
	        BindingResult bindingResult,
	        Model model,
	        @PathVariable Long employeeId,
	        HttpServletRequest request,
	        HttpServletResponse response) {
		
		if (bindingResult.hasErrors()) {
	        model.addAttribute("employeeId", employeeId);
	        return "/user/passwordChange";
	    }

	    // 新しいパスワードと確認用パスワードが一致するかチェック
	    if (!form.getNewPass().equals(form.getNewPassRev())) {
	        bindingResult.rejectValue("newPassRev", "error.newPassRev", "新しいパスワードが一致しません");
	        model.addAttribute("employeeId", employeeId);
	        return "/user/passwordChange";
	    }
	    System.out.println("form.getCurrentPass():"+form.getCurrentPass());

	    // ここで現在のパスワードが正しいかチェックする（サービス層で実装）
	    boolean isCurrentPassValid = employeeService.checkPassword(employeeId, form.getCurrentPass());
	    if (!isCurrentPassValid) {
	        bindingResult.rejectValue("currentPass", "error.currentPass", "現在のパスワードが正しくありません");
	        model.addAttribute("employeeId", employeeId);
	        return "/user/passwordChange";
	    }

	    // パスワード変更処理
	    employeeService.changePassword(employeeId, form.getNewPass());
	    employeeService.changeIsPassword (employeeId);
	    System.out.println("パスワード変更成功");	    

	    // ログインページへリダイレクト
	    return "redirect:/{employeeId}/top";
	}
}
