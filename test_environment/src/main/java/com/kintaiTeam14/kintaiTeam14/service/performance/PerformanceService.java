package com.kintaiTeam14.kintaiTeam14.service.performance;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.kintaiTeam14.kintaiTeam14.entity.Performance;

public interface PerformanceService {
    List<Performance> getAllPerformances(Long userId);
    List<Performance> getAllPerformances1(Long userId);

    boolean existsPerformancesForYear(Long userId, LocalDate startDate, LocalDate endDate);
    void createPerformancesForYear(Long userId, LocalDate startDate, LocalDate endDate);

    // 追加
    List<Performance> findByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate);

    // 新規追加：祝日をDBから取得するメソッド
    Set<LocalDate> findHolidaysBetween(LocalDate startDate, LocalDate endDate);
}