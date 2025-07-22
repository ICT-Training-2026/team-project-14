package com.kintaiTeam14.kintaiTeam14.controller.performance;

import java.time.LocalDate;
import java.time.Year;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.kintaiTeam14.kintaiTeam14.service.performance.PerformanceService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PerformanceController {
    private final PerformanceService performanceService;

    @PostMapping("/{employeeId}/top/jisseki_user")
    public String showPerformance(@PathVariable Long employeeId, Model model) {
        LocalDate startDate = Year.now().atDay(1);
        LocalDate endDate = Year.now().atMonth(12).atEndOfMonth();

        boolean exists = performanceService.existsPerformancesForYear(employeeId, startDate, endDate);
        if (!exists) {
            performanceService.createPerformancesForYear(employeeId, startDate, endDate);
        }

        var all = performanceService.getAllPerformances(employeeId);
        model.addAttribute("performances", all);
        model.addAttribute("userId", employeeId);
        return "performance/performance";
    }
}