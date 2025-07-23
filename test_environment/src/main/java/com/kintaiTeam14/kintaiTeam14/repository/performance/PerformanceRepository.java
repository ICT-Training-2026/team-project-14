// PerformanceRepository.java
package com.kintaiTeam14.kintaiTeam14.repository.performance;

import java.time.LocalDate;
import java.util.List;

import com.kintaiTeam14.kintaiTeam14.entity.Performance;

public interface PerformanceRepository {
    List<Performance> findAll(Long userId);

    boolean existsByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

    boolean existsByUserIdAndDate(Long userId, LocalDate date);

    void createPerformancesForYear(Long userId, LocalDate startDate, LocalDate endDate);

    void createAttendanceWithReason(Long userId, LocalDate date);

    // 追加
    List<Performance> findByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate);
}