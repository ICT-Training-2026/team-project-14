package com.kintaiTeam14.kintaiTeam14.controller.adminperformance;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kintaiTeam14.kintaiTeam14.service.adminperformance.AdminPerformanceService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminPerformanceController {

    private static final Logger logger = LoggerFactory.getLogger(AdminPerformanceController.class);

    private final AdminPerformanceService attendanceService;

    @GetMapping("/admin/achievement/list")
    public String showAttendance(
            @RequestParam(value = "employeeId", required = false) Integer employeeId,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month,
            Model model) {

        logger.info("Received parameters: employeeId={}, year={}, month={}", employeeId, year, month);

        List attendanceList = Collections.emptyList();

        if (employeeId != null && year != null && month != null) {
            attendanceList = attendanceService.getAttendanceWithReasonList(employeeId, year, month);
            logger.info("Retrieved {} attendance records", attendanceList.size());
        } else {
            logger.info("Parameters incomplete, returning empty attendance list");
        }

        model.addAttribute("attendanceList", attendanceList);
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("employeeId", employeeId);

        return "admin/achievement";
    }
}