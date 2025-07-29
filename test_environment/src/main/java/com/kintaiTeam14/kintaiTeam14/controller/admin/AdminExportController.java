package com.kintaiTeam14.kintaiTeam14.controller.admin;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kintaiTeam14.kintaiTeam14.entity.Attendance;
import com.kintaiTeam14.kintaiTeam14.service.attendance.AttendanceExportService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminExportController {
    private final AttendanceExportService attendanceExportService;

    @GetMapping("/export-attendance-zip")
    public void exportAttendanceZip(HttpServletResponse response) throws Exception {
        DateTimeFormatter ymFmt = DateTimeFormatter.ofPattern("yyyyMM");
        String zipName = LocalDate.now().format(ymFmt) + ".zip";
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=" + zipName);

//        Map<Integer, Map<String, List<Attendance>>> userMonthAttendance = attendanceExportService.getAttendanceByUserAndMonth();
        Map<Integer, Map<String, List<Attendance>>> userMonthAttendance = attendanceExportService.getAttendanceByUserAndMonthPrevMonthOnly();
        try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
            for (Integer empCode : userMonthAttendance.keySet()) {
                Map<String, List<Attendance>> monthMap = userMonthAttendance.get(empCode);
                for (String ym : monthMap.keySet()) {
                    int year = Integer.parseInt(ym.substring(0, 4));
                    int month = Integer.parseInt(ym.substring(4, 6));
                    int stdDays = attendanceExportService.calcStandardWorkingDays(year, month);
                    int stdMinutes = stdDays * 420; // 7h=420分

                    int sumWork = 0;
                    List<String> lines = new ArrayList<>();
                    lines.add("社員コード,年月,始業時刻(時),始業時刻(分),終業時刻(時),終業時刻(分),労働時間(分),休憩時間(分),超過時間(分)");
                    for (Attendance a : monthMap.get(ym)) {
                        String startH = (a.getArrivalTime() != null)
                                ? String.format("%02d", a.getArrivalTime().getHour())
                                : "00";
                        String startM = (a.getArrivalTime() != null)
                                ? String.format("%02d", a.getArrivalTime().getMinute())
                                : "00";
                        String endH = (a.getEndTime() != null) ? String.format("%02d", a.getEndTime().getHour()) : "00";
                        String endM = (a.getEndTime() != null) ? String.format("%02d", a.getEndTime().getMinute())
                                : "00";
                        // 休憩時間は「時間」単位なので「分」に変換
                        int breakHour = a.getBreakTime() != null ? a.getBreakTime() : 0;
                        int breakMin = breakHour * 60;
                        int workMin = 0;
                        // (終業-始業) - 休憩時間  （分）
                        if (a.getArrivalTime() != null && a.getEndTime() != null) {
                            workMin = (int) Duration.between(a.getArrivalTime(), a.getEndTime()).toMinutes() - breakMin;
                            if (workMin < 0) workMin = 0;
                        }
                        sumWork += workMin;
                        int overtime = Math.max(0, sumWork - stdMinutes);
                        // 出力（年月は yyyy-MM 形式で表示）
                        lines.add(String.format("%d,%d-%02d,%s,%s,%s,%s,%d,%d,%d",
                                empCode, year, month, startH, startM, endH, endM, workMin, breakMin, overtime));
//                        lines.add("DEBUG 所定労働日数" + stdDays);
                    }
                    String fileName = empCode + "_" + ym + ".csv";
                    zos.putNextEntry(new ZipEntry(fileName));
                    zos.write(new byte[]{(byte)0xEF, (byte)0xBB, (byte)0xBF});
                    for (String line : lines) {
                        zos.write((line + "\r\n").getBytes(StandardCharsets.UTF_8));
                    }
                    zos.closeEntry();
                }
            }
            zos.finish();
        }
        response.flushBuffer();
    }
}