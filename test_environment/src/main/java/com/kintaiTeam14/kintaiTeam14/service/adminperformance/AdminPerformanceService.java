package com.kintaiTeam14.kintaiTeam14.service.adminperformance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kintaiTeam14.kintaiTeam14.dto.AttendanceWithReasonDto;
import com.kintaiTeam14.kintaiTeam14.repository.adminperformance.AdminPerformanceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminPerformanceService {

    private final AdminPerformanceRepository attendanceRepository;

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

            // breakTimeの変換
            Object breakTimeObj = row[5];
            Integer breakTime = null;
            if (breakTimeObj instanceof Byte) {
                breakTime = ((Byte) breakTimeObj).intValue();
            } else if (breakTimeObj instanceof Integer) {
                breakTime = (Integer) breakTimeObj;
            }

            // atClassificationの変換
            Object atClassificationObj = row[6];
            Integer atClassification = null;
            if (atClassificationObj instanceof Byte) {
                atClassification = ((Byte) atClassificationObj).intValue();
            } else if (atClassificationObj instanceof Integer) {
                atClassification = (Integer) atClassificationObj;
            }

            String status = (String) row[7];
            String reason = (String) row[8];

            AttendanceWithReasonDto dto = new AttendanceWithReasonDto(
                attendId, employeeIdVal, date, arrivalTime, endTime,
                breakTime, atClassification, status, reason);

            dtoList.add(dto);
        }
        return dtoList;
    }
}
