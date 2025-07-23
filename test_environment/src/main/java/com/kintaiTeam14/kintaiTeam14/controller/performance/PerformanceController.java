package com.kintaiTeam14.kintaiTeam14.controller.performance;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kintaiTeam14.kintaiTeam14.entity.Performance;
import com.kintaiTeam14.kintaiTeam14.service.performance.PerformanceService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PerformanceController {
    private final PerformanceService performanceService;
	@PostMapping("/{employeeId}/top/jisseki_user")
    public String showPerformance(@PathVariable Long employeeId,Model model) {
		// System.out.println("year: " + request.getYear());
        // System.out.println("month: " + request.getMonth());

        var all = performanceService.getAllPerformances(employeeId);
        model.addAttribute("performances", all);
        model.addAttribute("userId", employeeId);
        return "performance/performance";
    }

	@PostMapping("/performance-update")
    public ResponseEntity<Void> updatePerformance(@RequestBody Performance performance) {
		performanceService.updatePerformance(performance);
        return ResponseEntity.ok().build();
    }

}