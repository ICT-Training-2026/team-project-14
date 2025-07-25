package com.kintaiTeam14.kintaiTeam14.service.attendance;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kintaiTeam14.kintaiTeam14.entity.Attendance;
import com.kintaiTeam14.kintaiTeam14.service.holiday.HolidayService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceExportService {
    private final AttendanceService attendanceService;
    private final HolidayService holidayService;

    // 社員ごと・月ごとに勤怠をグループ化
    public Map<Integer, Map<String, List<Attendance>>> getAttendanceByUserAndMonth() {
        List<Attendance> all = attendanceService.findAll();
        Map<Integer, Map<String, List<Attendance>>> result = new HashMap<>();
        DateTimeFormatter ymFmt = DateTimeFormatter.ofPattern("yyyyMM");
        for (Attendance att : all) {
            if (att.getEmployeeId() == null || att.getDate() == null) continue;
            int emp = att.getEmployeeId();
            String ym = att.getDate().format(ymFmt);
            result.computeIfAbsent(emp, k -> new HashMap<>())
                    .computeIfAbsent(ym, k -> new ArrayList<>())
                    .add(att);
        }
        return result;
    }
    
    public Map<Integer, Map<String, List<Attendance>>> getAttendanceByUserAndMonthPrevMonthOnly() {
        List<Attendance> all = attendanceService.findAll();
        Map<Integer, Map<String, List<Attendance>>> result = new HashMap<>();
        DateTimeFormatter ymFmt = DateTimeFormatter.ofPattern("yyyyMM");
        YearMonth prev = YearMonth.now().minusMonths(1);
        String prevYmStr = prev.format(ymFmt);

        for (Attendance att : all) {
            if (att.getEmployeeId() == null || att.getDate() == null) continue;
            String ym = att.getDate().format(ymFmt);
            if (!ym.equals(prevYmStr)) continue; // 前月以外はスキップ
            int emp = att.getEmployeeId();
            result.computeIfAbsent(emp, k -> new HashMap<>())
                  .computeIfAbsent(ym, k -> new ArrayList<>())
                  .add(att);
        }
        return result;
    }
    // 指定年月の所定労働日数（土日・休日・振休を除外）
    public int calcStandardWorkingDays(int year, int month) {
        LocalDate first = LocalDate.of(year, month, 1);
        LocalDate last = first.withDayOfMonth(first.lengthOfMonth());
        List<LocalDate> holidays = holidayService.findHolidayDatesInMonth(year, month);
        int count = 0;
        for (LocalDate d = first; !d.isAfter(last); d = d.plusDays(1)) {
            if (d.getDayOfWeek() != DayOfWeek.SATURDAY
                    && d.getDayOfWeek() != DayOfWeek.SUNDAY
                    && !holidays.contains(d)) {
                count++;
            }
        }
        return count;
    }
}