package com.kintaiTeam14.kintaiTeam14.controller.performance;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.kintaiTeam14.kintaiTeam14.service.performance.PerformanceService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PerformanceController {
    private final PerformanceService performanceService;
    @GetMapping("/{userId}/top/performance")
    public String showPerformance(@PathVariable Long userId,Model model) {
        var all = performanceService.getAllPerformances();
        model.addAttribute("performances", all);
        model.addAttribute("userId", userId);
        return "performance/performance";
    }
}