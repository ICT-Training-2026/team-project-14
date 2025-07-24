package com.kintaiTeam14.kintaiTeam14.repository.adminperformance;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kintaiTeam14.kintaiTeam14.entity.AdminAttendance;

@Repository
public interface AdminPerformanceRepository extends JpaRepository<AdminAttendance, Long> {

    /**
     * 社員IDと日付範囲で勤怠情報を取得し、関連するreasonもフェッチジョインで取得する
     *
     * @param employeeId 社員ID
     * @param startDate  開始日付（含む）
     * @param endDate    終了日付（含む）
     * @return 勤怠実績リスト（昇順ソート）
     */
    @Query("SELECT a FROM AdminAttendance a LEFT JOIN FETCH a.reason WHERE a.employee.employeeId = :employeeId AND a.date BETWEEN :startDate AND :endDate ORDER BY a.date ASC")
    List<AdminAttendance> findWithReasonByEmployeeIdAndDateBetweenOrderByDateAsc(
        @Param("employeeId") Integer employeeId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate);

    /**
     * 社員IDと日付範囲で勤怠情報をネイティブSQLのLEFT JOINで取得する
     * 戻り値はObject配列のリストとなるため、Service層等でDTOに変換して利用する
     *
     * @param employeeId 社員ID
     * @param startDate  開始日付（含む）
     * @param endDate    終了日付（含む）
     * @return Object配列のリスト
     */
    @Query(value = "SELECT a.attend_id, a.employee_id, a.date, a.arrival_time, a.end_time, a.break_time, a.at_classification, a.status, r.reason " +
                   "FROM attendance a LEFT JOIN reason r ON a.attend_id = r.attend_id " +
                   "WHERE a.employee_id = :employeeId AND a.date BETWEEN :startDate AND :endDate " +
                   "ORDER BY a.date ASC", nativeQuery = true)
    List<Object[]> findAttendanceWithReasonNative(
        @Param("employeeId") Integer employeeId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate);
}