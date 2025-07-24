package com.kintaiTeam14.kintaiTeam14.controller.adminperformance;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kintaiTeam14.kintaiTeam14.entity.AdminAttendance;
import com.kintaiTeam14.kintaiTeam14.service.adminperformance.AdminPerformanceService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminPerformanceController {

    private final AdminPerformanceService attendanceService;

    @GetMapping("/admin/achievement/list")
    public String showAttendance(
            @RequestParam("employeeId") Integer employeeId,
            @RequestParam("year") int year,
            @RequestParam("month") int month,
            Model model) {

        List<AdminAttendance> attendanceList = attendanceService.getAttendanceList(employeeId, year, month);

        model.addAttribute("attendanceList", attendanceList);
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("employeeId", employeeId);

        return "achievement";
    }
}