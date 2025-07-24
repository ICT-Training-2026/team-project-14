package com.kintaiTeam14.kintaiTeam14.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AdminMenuController {

    /** ユーザー管理画面 */
    @GetMapping("/admin/User-management")
    public String userManagement() {
        return "admin/user-management"; // templates/admin/user-management.html
    }

    /** 実績画面 */
    @GetMapping("/admin/achievement")
    public String achievement(@ModelAttribute Model model) {
        return "admin/achievement"; // templates/admin/achievement.html
    }

    /** 承認・訂正依頼画面 */
    @GetMapping("/admin/approval-correction")
    public String approvalCorrection() {
        return "admin/approval-correction"; // templates/admin/approval-correction.html
    }

    /** 会社情報画面 */
    @GetMapping("/admin/company-info")
    public String companyInfo() {
        return "admin/company-info"; // templates/admin/company-info.html
    }
}