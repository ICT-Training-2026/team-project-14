// PerformanceRepository.java
package com.kintaiTeam14.kintaiTeam14.repository.performance;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.kintaiTeam14.kintaiTeam14.entity.AdminPerformance;
import com.kintaiTeam14.kintaiTeam14.entity.Performance;
import com.kintaiTeam14.kintaiTeam14.entity.RePerformance;

public interface PerformanceRepository {
    List<Performance> findAll(Long userId);

    boolean existsByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

    boolean existsByUserIdAndDate(Long userId, LocalDate date);

    void createPerformancesForYear(Long userId, LocalDate startDate, LocalDate endDate,Set<LocalDate>holidays);

    void createAttendanceWithReason(Long userId, LocalDate date,int atAttendance);

    List<AdminPerformance> findSubmitAll();





    // 追加
    List<Performance> findByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate);
    List<RePerformance> findByreId(int reId);
    void updatePerformance(Performance performance);
    void updateRePerformance(RePerformance reperformance);
    void updateAdminPerformance(AdminPerformance adminperformance);

}