package com.kintaiTeam14.kintaiTeam14.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kintaiTeam14.kintaiTeam14.entity.Holiday;

public interface PerformanceHolidayRepository extends JpaRepository<Holiday, Integer> {

    // 指定期間の祝日を取得するメソッド
    List<Holiday> findByHolidayDateBetween(LocalDate startDate, LocalDate endDate);

}