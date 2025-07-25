package com.kintaiTeam14.kintaiTeam14.service.performance;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.kintaiTeam14.kintaiTeam14.entity.Performance;
import com.kintaiTeam14.kintaiTeam14.entity.RePerformance;

public interface PerformanceService {
    List getAllPerformances(Long userId);
    List getAllPerformances1(Long userId);

    boolean existsPerformancesForYear(Long userId, LocalDate startDate, LocalDate endDate);
    void createPerformancesForYear(Long userId, LocalDate startDate, LocalDate endDate);

    // 追加
    List findByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate);

    List findByreId(int reId);

    // 祝日取得
    Set findHolidaysBetween(LocalDate startDate, LocalDate endDate);

    // 新規追加：月間所定労働時間(時間数)
    double calculateScheduledWorkHours(Long userId, LocalDate startDate, LocalDate endDate);

    // 新規追加：実労働時間(時間数)
    double calculateActualWorkHours(Long userId, LocalDate startDate, LocalDate endDate);

    // 新規追加：残業時間(時間数)
    double calculateOvertimeHours(Long userId, LocalDate startDate, LocalDate endDate);

    // 新規追加：残年休日数取得
    int getRemainingPaidHoliday(Long userId);

    // 新規追加：残振休日数取得
    int getRemainingCompDay(Long userId);

    void updatePerformance(Performance performance);
    void updateRePerformance(RePerformance reperformance);

}