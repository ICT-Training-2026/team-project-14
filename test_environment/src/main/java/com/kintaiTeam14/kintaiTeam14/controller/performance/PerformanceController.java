package com.kintaiTeam14.kintaiTeam14.controller.performance;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.kintaiTeam14.kintaiTeam14.service.performance.PerformanceService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PerformanceController {
    private final PerformanceService performanceService;

    @PostMapping("/{employeeId}/top/jisseki_user")
    public String showPerformancePost(
            @PathVariable Long employeeId,
            Model model) {

        int y = 2025;
        int m = 7;

        YearMonth yearMonth = YearMonth.of(y, m);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        var performances = performanceService.findByUserIdAndDateRange(employeeId, startDate, endDate);

        // 祝日をDBから取得
        Set<LocalDate> holidays = performanceService.findHolidaysBetween(startDate, endDate);

        model.addAttribute("performances", performances);
        model.addAttribute("userId", employeeId);
        model.addAttribute("year", y);
        model.addAttribute("month", m);
        model.addAttribute("holidays", holidays);

        return "performance/performance";
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