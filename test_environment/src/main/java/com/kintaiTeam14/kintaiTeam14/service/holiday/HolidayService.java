package com.kintaiTeam14.kintaiTeam14.service.holiday;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kintaiTeam14.kintaiTeam14.entity.Holiday;
import com.kintaiTeam14.kintaiTeam14.repository.holiday.HolidayRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HolidayService {
    private final HolidayRepository holidayRepository;

    public List<Holiday> findAll() {
        return holidayRepository.findAll();
    }

    public Optional<Holiday> findById(Integer id) {
        return holidayRepository.findById(id);
    }

    public Holiday save(Holiday holiday) {
        return holidayRepository.save(holiday);
    }

    public void deleteById(Integer id) {
        holidayRepository.deleteById(id);
    }

    // 追加: 指定月の休日・振休日リスト
    public List<LocalDate> findHolidayDatesInMonth(int year, int month) {
        return holidayRepository.findHolidayDatesInMonth(year, month);
    }
}