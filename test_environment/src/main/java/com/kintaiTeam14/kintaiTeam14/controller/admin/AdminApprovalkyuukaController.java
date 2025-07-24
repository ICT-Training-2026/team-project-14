package com.kintaiTeam14.kintaiTeam14.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminApprovalkyuukaController {
	@PostMapping("/admin/approval-correction/approval-kyuuka")
	public String approvalKyuuka(Model m) {
		
		
		
		
		return "test";
	}
	
	

}
