package com.kintaiTeam14.kintaiTeam14.controller.performance;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
        System.out.println("all:");
        System.out.println(all);
        model.addAttribute("performances", all);
        model.addAttribute("userId", employeeId);
        int currentYearup = LocalDate.now().getYear();
        int currentYeardown=currentYearup-5;
        model.addAttribute("currentYeardown", currentYeardown);
        model.addAttribute("currentYearup", currentYearup);
        return "performance/performance";
    }

	@PostMapping("/performance-update")
    public ResponseEntity<Void> updatePerformance(@RequestBody Performance performance) {
        System.out.println("AWS");
        performanceService.updatePerformance(performance);
        return ResponseEntity.ok().build();
    }

	@GetMapping("/{employeeId}/top/jisseki_user")
    public String showPerformance(
            @PathVariable Long employeeId,
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "month", required = false) Integer month,
            Model model) {

        LocalDate now = LocalDate.now();
        int y = (year != null) ? year : now.getYear();
        int m = (month != null) ? month : now.getMonthValue();

        YearMonth current = YearMonth.of(y, m);
        LocalDate startDate = current.atDay(1);
        LocalDate endDate = current.atEndOfMonth();

        var performances = performanceService.findByUserIdAndDateRange(employeeId, startDate, endDate);

        // 祝日をDBから取得
        Set<LocalDate> holidays = performanceService.findHolidaysBetween(startDate, endDate);

        YearMonth prev = current.minusMonths(1);
        YearMonth next = current.plusMonths(1);

        model.addAttribute("performances", performances);
        model.addAttribute("userId", employeeId);
        model.addAttribute("year", y);
        model.addAttribute("month", m);
        model.addAttribute("prevYear", prev.getYear());
        model.addAttribute("prevMonth", prev.getMonthValue());
        model.addAttribute("nextYear", next.getYear());
        model.addAttribute("nextMonth", next.getMonthValue());
        model.addAttribute("holidays", holidays);

        return "performance/performance";
    }

}