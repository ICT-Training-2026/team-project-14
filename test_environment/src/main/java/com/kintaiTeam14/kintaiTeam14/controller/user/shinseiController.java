package com.kintaiTeam14.kintaiTeam14.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.kintaiTeam14.kintaiTeam14.form.ChangePasswordForm;

@Controller
public class shinseiController {

	/*	@PostMapping("/nenkyu")
		public String nenkyu() {
			return "user/nenkyu";
		}*/
	@PostMapping("/{employeeId}/top/shinsei/nenkyu")
	public String ShowNenkyu(@PathVariable("employeeId")Long employeeId, Model model) {
		model.addAttribute("employeeId", employeeId);
	
		    model.addAttribute("form", new ChangePasswordForm()); 
		return"user/nenkyu";
	}
	
	/*	@PostMapping("/hurikyu")
		public String hurikyu() {
			return "user/hurikyu";
		}*/
	
	@PostMapping("/{employeeId}/top/shinsei/hurikyu")
	public String ShowHrikyu(@PathVariable("employeeId")Long employeeId, Model model) {
		model.addAttribute("employeeId", employeeId);
	
		    model.addAttribute("form", new ChangePasswordForm()); 
		return"user/hurikyu";
	}
}
