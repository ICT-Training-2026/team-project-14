package com.kintaiTeam14.kintaiTeam14.repository.adminperformance;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kintaiTeam14.kintaiTeam14.entity.Holiday;

@Repository
public interface AdminHolidayRepository extends JpaRepository<Holiday, Integer> {

    /**
     * 指定期間内の祝日の日付リストを取得する
     *
     * @param startDate 期間開始日
     * @param endDate 期間終了日
     * @return 祝日の日付リスト
     */
    @Query("SELECT h.holidayDate FROM Holiday h WHERE h.holidayDate BETWEEN :startDate AND :endDate")
    List<LocalDate> findHolidayDatesBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}