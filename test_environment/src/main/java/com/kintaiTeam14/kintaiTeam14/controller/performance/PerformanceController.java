package com.kintaiTeam14.kintaiTeam14.controller.performance;

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
    public String showPerformance(@PathVariable Long employeeId,Model model) {
		
        var all = performanceService.getAllPerformances(employeeId);
        model.addAttribute("performances", all);
        model.addAttribute("userId", employeeId);
        return "performance/performance";
    }
}