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

    // POSTメソッドで年・月をパラメータで受け取る（デフォルトは現在の年月）
    @PostMapping("/{employeeId}/top/jisseki_user")
    public String showPerformancePost(
            @PathVariable Long employeeId,
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "month", required = false) Integer month,
            Model model) {

        LocalDate now = LocalDate.now();
        int y = (year != null) ? year : now.getYear();
        int m = (month != null) ? month : now.getMonthValue();

        YearMonth yearMonth = YearMonth.of(y, m);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        var performances = performanceService.findByUserIdAndDateRange(employeeId, startDate, endDate);

        // 祝日をDBから取得（祝日＋土日も含む）
        Set<LocalDate> holidays = performanceService.findHolidaysBetween(startDate, endDate);

        // 集計値を計算
        double scheduledHours = performanceService.calculateScheduledWorkHours(employeeId, startDate, endDate);
        double actualHours = performanceService.calculateActualWorkHours(employeeId, startDate, endDate);
        double overtimeHours = performanceService.calculateOvertimeHours(employeeId, startDate, endDate);
        int paidHoliday = performanceService.getRemainingPaidHoliday(employeeId);
        int compDay = performanceService.getRemainingCompDay(employeeId);

        model.addAttribute("performances", performances);
        model.addAttribute("userId", employeeId);
        model.addAttribute("year", y);
        model.addAttribute("month", m);
        model.addAttribute("holidays", holidays);

        // 前月・翌月の年月を計算しモデルにセット
        YearMonth prev = yearMonth.minusMonths(1);
        YearMonth next = yearMonth.plusMonths(1);

        model.addAttribute("prevYear", prev.getYear());
        model.addAttribute("prevMonth", prev.getMonthValue());
        model.addAttribute("nextYear", next.getYear());
        model.addAttribute("nextMonth", next.getMonthValue());

        model.addAttribute("scheduledHours", scheduledHours);
        model.addAttribute("actualHours", actualHours);
        model.addAttribute("overtimeHours", overtimeHours);
        model.addAttribute("paidHoliday", paidHoliday);
        model.addAttribute("compDay", compDay);

        return "performance/performance";
    }

    // GETメソッドで年・月をパラメータで受け取る（デフォルトは現在の年月）
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
        Set<LocalDate> holidays = performanceService.findHolidaysBetween(startDate, endDate);

        double scheduledHours = performanceService.calculateScheduledWorkHours(employeeId, startDate, endDate);
        double actualHours = performanceService.calculateActualWorkHours(employeeId, startDate, endDate);
        double overtimeHours = performanceService.calculateOvertimeHours(employeeId, startDate, endDate);
        int paidHoliday = performanceService.getRemainingPaidHoliday(employeeId);
        int compDay = performanceService.getRemainingCompDay(employeeId);

        model.addAttribute("performances", performances);
        model.addAttribute("userId", employeeId);
        model.addAttribute("year", y);
        model.addAttribute("month", m);

        // 前月・翌月の年月を計算しモデルにセット
        YearMonth prev = current.minusMonths(1);
        YearMonth next = current.plusMonths(1);

        model.addAttribute("prevYear", prev.getYear());
        model.addAttribute("prevMonth", prev.getMonthValue());
        model.addAttribute("nextYear", next.getYear());
        model.addAttribute("nextMonth", next.getMonthValue());

        model.addAttribute("holidays", holidays);

        model.addAttribute("scheduledHours", scheduledHours);
        model.addAttribute("actualHours", actualHours);
        model.addAttribute("overtimeHours", overtimeHours);
        model.addAttribute("paidHoliday", paidHoliday);
        model.addAttribute("compDay", compDay);

        return "performance/performance";
    }
}
