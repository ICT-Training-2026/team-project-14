package com.kintaiTeam14.kintaiTeam14.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/approval-correction")
public class ApprovalCorrectionController {

    // メニュー画面
//    @GetMapping("")
//    public String approvalCorrectionMenu() {
//        // templates/admin/approval-correction.html
//        return "admin/approval-correction";
//    }

    // 実績承認画面
    @GetMapping("/approval-achievement")
    public String approvalAchievement() {
        // templates/admin/approval-achievement.html
        return "admin/approval-achievement";
    }

    // 年休・振休承認画面
    @GetMapping("/approval-kyuuka")
    public String approvalKyuuka() {
        // templates/admin/approval-kyuuka.html
        return "admin/approval-kyuuka";
    }
}