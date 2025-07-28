package com.kintaiTeam14.kintaiTeam14.controller.setting;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
public class Setting {
	 private final EmployeeService employeeService;

	@GetMapping("/{employeeId}/passChange_form_user")
	@PreAuthorize("#employeeId== principal.employeeId")
	public String ShowpasswordSetting(@PathVariable("employeeId")Long employeeId, Model model) {
		model.addAttribute("employeeId", employeeId);
	
		    model.addAttribute("form", new ChangePasswordForm()); 
		return "/user/passwordChange_fast" ;
	}
	@PostMapping("/{employeeId}/passChange_form_user")
	@PreAuthorize("#employeeId== principal.employeeId")
	public String passwordSetting(
	        @Valid @ModelAttribute("form") ChangePasswordForm form,
	        BindingResult bindingResult,
	        Model model,
	        @PathVariable Long employeeId,
	        HttpServletRequest request,
	        HttpServletResponse response) {
		
		if (bindingResult.hasErrors()) {
	        model.addAttribute("employeeId", employeeId);
	        model.addAttribute("errorMessage", "英数字記号を含む８文字以上にしてください");
	        return "/user/passwordChange_fast";
	    }

	    // 新しいパスワードと確認用パスワードが一致するかチェック
	    if (!form.getNewPass().equals(form.getNewPassRev())) {
	        bindingResult.rejectValue("newPassRev", "error.newPassRev", "新しいパスワードが一致しません");
	        model.addAttribute("errorMessage", "新しいパスワードが一致しません");
	        model.addAttribute("employeeId", employeeId);
	        return "/user/passwordChange_fast";
	    }
	    System.out.println("form.getCurrentPass():"+form.getCurrentPass());

	    // ここで現在のパスワードが正しいかチェックする（サービス層で実装）
	    boolean isCurrentPassValid = employeeService.checkPassword(employeeId, form.getCurrentPass());
	    if (!isCurrentPassValid) {
	        bindingResult.rejectValue("currentPass", "error.currentPass", "現在のパスワードが正しくありません");
	        model.addAttribute("employeeId", employeeId);
	        model.addAttribute("errorMessage", "現在のパスワードが正しくありません");
	        return "/user/passwordChange_fast";
	    }

	    // パスワード変更処理
	    employeeService.changePassword(employeeId, form.getNewPass());
	    employeeService.changeIsPassword (employeeId);
	    

	    // 現在の認証情報を取得
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null) {
	        // ログアウト処理を実行
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }

	    // ログインページへリダイレクト
	    return "redirect:/login";
	}

}
