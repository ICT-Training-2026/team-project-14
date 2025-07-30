package com.kintaiTeam14.kintaiTeam14.service.adminperformance;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kintaiTeam14.kintaiTeam14.dto.AttendanceWithReasonDto;
import com.kintaiTeam14.kintaiTeam14.entity.AdminEmployee;
import com.kintaiTeam14.kintaiTeam14.repository.adminperformance.AdminEmployeeRepository;
import com.kintaiTeam14.kintaiTeam14.repository.adminperformance.AdminPerformanceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminPerformanceService {

    private static final Logger logger = LoggerFactory.getLogger(AdminPerformanceService.class);

    private final AdminPerformanceRepository attendanceRepository;
    private final AdminEmployeeRepository employeeRepository;

    /**
     * 社員IDと年月で勤怠一覧をDTOで取得する（reasonも含む）
     *
     * @param employeeId 社員ID
     * @param year       年
     * @param month      月
     * @return 勤怠実績DTOリスト
     */
    public List<AttendanceWithReasonDto> getAttendanceWithReasonList(Integer employeeId, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        List<Object[]> results = attendanceRepository.findAttendanceWithReasonNative(employeeId, startDate, endDate);

        List<AttendanceWithReasonDto> dtoList = new ArrayList<>();
        for (Object[] row : results) {
            Long attendId = ((Number) row[0]).longValue();
            Integer employeeIdVal = ((Number) row[1]).intValue();

            java.sql.Date sqlDate = (java.sql.Date) row[2];
            LocalDate date = sqlDate != null ? sqlDate.toLocalDate() : null;

            java.sql.Timestamp sqlArrival = (java.sql.Timestamp) row[3];
            LocalDateTime arrivalTime = sqlArrival != null ? sqlArrival.toLocalDateTime() : null;

            java.sql.Timestamp sqlEnd = (java.sql.Timestamp) row[4];
            LocalDateTime endTime = sqlEnd != null ? sqlEnd.toLocalDateTime() : null;

            // breakTimeをDouble型で取得
            Object breakTimeObj = row[5];
            Double breakTime = null;
            if (breakTimeObj instanceof Number) {
                breakTime = ((Number) breakTimeObj).doubleValue();
            }

            // Boolean対応でatClassificationをIntegerに変換
            Object atClassificationObj = row[6];
            Integer atClassification = null;
            if (atClassificationObj instanceof Boolean) {
                atClassification = ((Boolean) atClassificationObj) ? 1 : 0;
            } else if (atClassificationObj instanceof Number) {
                atClassification = ((Number) atClassificationObj).intValue();
            }

            // ログ追加：値と型の確認
            logger.debug("atClassificationObj: {}, class: {}, mapped value: {}", atClassificationObj,
                atClassificationObj != null ? atClassificationObj.getClass().getName() : "null", atClassification);

            String status = (String) row[7];
            String reason = (String) row[8];

            AttendanceWithReasonDto dto = new AttendanceWithReasonDto(
                attendId, employeeIdVal, date, arrivalTime, endTime,
                breakTime, atClassification, status, reason);

            dtoList.add(dto);
        }
        return dtoList;
    }

    /**
     * 社員IDと年月で実労働時間を計算する
     * 退勤 - 出勤 - 休憩時間 の合計時間（時間単位、小数点以下も含む）
     *
     * @param employeeId 社員ID
     * @param year       年
     * @param month      月
     * @return 実労働時間の合計（時間、小数点以下含む）
     */
    public double calculateActualWorkingHours(Integer employeeId, int year, int month) {
        List<AttendanceWithReasonDto> attendanceList = getAttendanceWithReasonList(employeeId, year, month);
        double totalHours = 0.0;

        for (AttendanceWithReasonDto attendance : attendanceList) {
            LocalDateTime arrival = attendance.getArrivalTime();
            LocalDateTime end = attendance.getEndTime();
            Double breakTime = attendance.getBreakTime(); // 休憩時間（時間単位。nullなら0）

            if (arrival != null && end != null) {
                long minutesWorked = Duration.between(arrival, end).toMinutes();
                double workHours = minutesWorked / 60.0; // 分→時間
                double breakHours = (breakTime != null) ? breakTime : 0.0;
                double netHours = workHours - breakHours;
                if (netHours > 0) {
                    totalHours += netHours;
                }
            }
        }
        logger.debug("Total actual working hours for employeeId {}: {}", employeeId, totalHours);
        return totalHours;
    }


    /**
     * 社員IDで社員情報を取得する
     *
     * @param employeeId 社員ID
     * @return AdminEmployeeのOptional
     */
    public Optional<AdminEmployee> getEmployeeById(Integer employeeId) {
        Optional<AdminEmployee> employeeOpt = employeeRepository.findByEmployeeId(employeeId);
        employeeOpt.ifPresent(emp -> logger.debug("Found employee: id={}, paidHoliday={}, compDay={}",
                emp.getEmployeeId(), emp.getPaidHoliday(), emp.getCompDay()));
        return employeeOpt;
    }
}
