package com.kintaiTeam14.kintaiTeam14.service.adminperformance;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kintaiTeam14.kintaiTeam14.entity.AdminAttendance;
import com.kintaiTeam14.kintaiTeam14.repository.adminperformance.AdminPerformanceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminPerformanceService {

    private final AdminPerformanceRepository attendanceRepository;

    /**
     * 社員IDと年月で勤怠一覧を取得する
     *
     * @param employeeId 社員ID
     * @param year       年
     * @param month      月
     * @return 勤怠実績リスト
     */
    public List<AdminAttendance> getAttendanceList(Integer employeeId, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        return attendanceRepository.findByEmployee_EmployeeIdAndDateBetweenOrderByDateAsc(employeeId, startDate, endDate);
    }
}