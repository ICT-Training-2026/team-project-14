package com.kintaiTeam14.kintaiTeam14.repository.adminperformance;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kintaiTeam14.kintaiTeam14.entity.AdminAttendance;

@Repository
public interface AdminPerformanceRepository extends JpaRepository<AdminAttendance, Long> {

    /**
     * 社員IDと日付範囲で勤怠情報を取得する
     *
     * @param employeeId 社員ID
     * @param startDate  開始日付（含む）
     * @param endDate    終了日付（含む）
     * @return 勤怠実績リスト（昇順ソート）
     */
    List<AdminAttendance> findByEmployee_EmployeeIdAndDateBetweenOrderByDateAsc(Integer employeeId, LocalDate startDate, LocalDate endDate);

}