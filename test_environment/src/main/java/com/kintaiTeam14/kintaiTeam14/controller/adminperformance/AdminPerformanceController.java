package com.kintaiTeam14.kintaiTeam14.controller.adminperformance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kintaiTeam14.kintaiTeam14.dto.AttendanceWithReasonDto;
import com.kintaiTeam14.kintaiTeam14.service.adminperformance.AdminPerformanceService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminPerformanceController {

    private static final Logger logger = LoggerFactory.getLogger(AdminPerformanceController.class);

    private final AdminPerformanceService attendanceService;

    /**
     * 初期表示および検索処理を一本化
     * URLを /admin/achievement/performance に変更
     */
    @GetMapping("/admin/achievement/performance")
    public String showAchievement(
            @RequestParam(value = "employeeId", required = false) Integer employeeId,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month,
            Model model) {

        logger.info("showAchievement called with employeeId={}, year={}, month={}", employeeId, year, month);

        int currentYear = LocalDate.now().getYear();

        // 年リスト（現在の年から5年前まで）
        List<Integer> yearList = new ArrayList<>();
        for (int y = currentYear; y >= currentYear - 5; y--) {
            yearList.add(y);
        }
        model.addAttribute("yearList", yearList);
        logger.info("yearList = {}", yearList);

        // 月リスト（1〜12）
        List<Integer> monthList = new ArrayList<>();
        for (int m = 1; m <= 12; m++) {
            monthList.add(m);
        }
        model.addAttribute("monthList", monthList);
        logger.info("monthList = {}", monthList);

        // yearがnullなら現在の年をセット
        if (year == null) {
            year = currentYear;
        }
        model.addAttribute("selectedYear", year);

        // monthがnullなら現在の月をセット
        if (month == null) {
            month = LocalDate.now().getMonthValue();
        }
        model.addAttribute("selectedMonth", month);

        List<AttendanceWithReasonDto> attendanceList = Collections.emptyList();

        // 社員番号と年・月がすべて入力されていれば検索実行
        if (employeeId != null && year != null && month != null) {
            attendanceList = attendanceService.getAttendanceWithReasonList(employeeId, year, month);
            logger.info("Retrieved {} attendance records", attendanceList.size());
        } else {
            logger.info("Parameters incomplete, returning empty attendance list");
        }

        model.addAttribute("attendanceList", attendanceList);
        model.addAttribute("employeeId", employeeId);

        return "admin/achievement";
    }
}