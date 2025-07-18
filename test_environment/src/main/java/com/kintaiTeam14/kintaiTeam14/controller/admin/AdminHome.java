package com.kintaiTeam14.kintaiTeam14.controller.admin;

import org.springframework.stereotype.Controller;
/*
 *管理者用ログインページ用パッケージになります
 *
 *
 */
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AdminHome {
	 @GetMapping("/admin")
	    public String adminHome() {
	        return "admin/home";
	    }

}
