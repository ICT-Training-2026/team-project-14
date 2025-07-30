package com.kintaiTeam14.kintaiTeam14.repository.holiday;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kintaiTeam14.kintaiTeam14.entity.Holiday;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Integer> {

    
    @Query("SELECT h.holidayDate FROM Holiday h WHERE (h.holidayType = '休日' OR h.holidayType = '振替休日' OR h.holidayType = '祝日') AND FUNCTION('YEAR', h.holidayDate) = :year AND FUNCTION('MONTH', h.holidayDate) = :month")
    List<LocalDate> findHolidayDatesInMonth(int year, int month);

}