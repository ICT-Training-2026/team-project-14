package com.kintaiTeam14.kintaiTeam14.controller.adminperformance;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import com.kintaiTeam14.kintaiTeam14.dto.AttendanceWithReasonDto;
import com.kintaiTeam14.kintaiTeam14.service.adminperformance.AdminHolidayService;
import com.kintaiTeam14.kintaiTeam14.service.adminperformance.AdminPerformanceService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminPerformanceController {

    private static final Logger logger = LoggerFactory.getLogger(AdminPerformanceController.class);

    private final AdminPerformanceService attendanceService;
    private final AdminHolidayService holidayService;

    @GetMapping("/admin/achievement/performance")
    public String showAchievement(
            @RequestParam(value = "employeeId", required = false) Integer employeeId,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month,
            Model model) {

        logger.info("showAchievement called with employeeId={}, year={}, month={}", employeeId, year, month);

        int currentYear = LocalDate.now().getYear();

        List<Integer> yearList = new ArrayList<>();
        for (int y = currentYear; y >= currentYear - 5; y--) {
            yearList.add(y);
        }
        model.addAttribute("yearList", yearList);
        logger.info("yearList = {}", yearList);

        List<Integer> monthList = new ArrayList<>();
        for (int m = 1; m <= 12; m++) {
            monthList.add(m);
        }
        model.addAttribute("monthList", monthList);
        logger.info("monthList = {}", monthList);

        if (year == null) {
            year = currentYear;
        }
        model.addAttribute("selectedYear", year);

        if (month == null) {
            month = LocalDate.now().getMonthValue();
        }
        model.addAttribute("selectedMonth", month);

        // 祝日を考慮した月間所定労働時間を計算し、double型にキャスト
        int scheduledWorkingHoursInt = holidayService.calculateScheduledWorkingHours(year, month);
        double scheduledWorkingHours = (double) scheduledWorkingHoursInt;
        model.addAttribute("scheduledWorkingHours", scheduledWorkingHours);

        List<AttendanceWithReasonDto> attendanceList = Collections.emptyList();
        double actualWorkingHours = 0.0;

        if (employeeId != null && year != null && month != null) {
            attendanceList = attendanceService.getAttendanceWithReasonList(employeeId, year, month);
            logger.info("Retrieved {} attendance records", attendanceList.size());

            actualWorkingHours = attendanceService.calculateActualWorkingHours(employeeId, year, month);
            logger.info("Calculated actual working hours: {}", actualWorkingHours);

            // atClassificationの数値を文字列に変換して新しいリストを作成
            List<Map<String, Object>> attendanceDisplayList = new ArrayList<>();
            Map<Integer, String> classificationMap = Map.of(
                1, "出社",
                2, "有給申請中",
                3, "振休申請中"
            );

            for (AttendanceWithReasonDto dto : attendanceList) {
                Map<String, Object> map = new HashMap<>();
                map.put("attendId", dto.getAttendId());
                map.put("employeeId", dto.getEmployeeId());
                map.put("date", dto.getDate());
                map.put("arrivalTime", dto.getArrivalTime());
                map.put("endTime", dto.getEndTime());
                map.put("breakTime", dto.getBreakTime());

                Integer classificationKey = dto.getAtClassification();
                String classificationStr;
                if (classificationKey == null) {
                    classificationStr = "エラー";
                } else if (classificationKey == 0) {
                    classificationStr = "";
                } else {
                    classificationStr = classificationMap.getOrDefault(classificationKey, "未設定");
                }
                map.put("classification", classificationStr);

                map.put("status", dto.getStatus());
                map.put("reason", dto.getReason());
                attendanceDisplayList.add(map);
            }
            model.addAttribute("attendanceList", attendanceDisplayList);

            // 社員情報を取得し、残年休日数と残振休日数をモデルにセット
            attendanceService.getEmployeeById(employeeId).ifPresentOrElse(employee -> {
                model.addAttribute("remainingAnnualLeave", employee.getPaidHoliday());
                model.addAttribute("remainingSubstituteHoliday", employee.getCompDay());
            }, () -> {
                model.addAttribute("remainingAnnualLeave", 0);
                model.addAttribute("remainingSubstituteHoliday", 0);
            });
        } else {
            logger.info("Parameters incomplete, returning empty attendance list");
            model.addAttribute("attendanceList", Collections.emptyList());
            model.addAttribute("remainingAnnualLeave", 0);
            model.addAttribute("remainingSubstituteHoliday", 0);
        }

        model.addAttribute("employeeId", employeeId);
        model.addAttribute("actualWorkingHours", actualWorkingHours);

        double overtimeHours = actualWorkingHours - scheduledWorkingHours;
        if (overtimeHours < 0) {
            overtimeHours = 0;
        }

        logger.info("actualWorkingHours = {}", actualWorkingHours);
        logger.info("scheduledWorkingHours = {}", scheduledWorkingHours);
        logger.info("overtimeHours (after adjustment) = {}", overtimeHours);

        DecimalFormat df = new DecimalFormat("#0.00");
        String formattedOvertimeHours = df.format(overtimeHours);
        logger.info("formattedOvertimeHours = {}", formattedOvertimeHours);

        model.addAttribute("formattedOvertimeHours", formattedOvertimeHours);

        return "admin/achievement";
    }

    @GetMapping("/admin/achievement/edit/{id}")
    public String editAttendance(@PathVariable("id") Long attendId, Model model) {
        model.addAttribute("message", "テスト");
        return "admin/achievementEdit";
    }
}
