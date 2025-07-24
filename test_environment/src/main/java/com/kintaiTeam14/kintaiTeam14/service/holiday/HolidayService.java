package com.kintaiTeam14.kintaiTeam14.service.holiday;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kintaiTeam14.kintaiTeam14.entity.Holiday;
import com.kintaiTeam14.kintaiTeam14.repository.holiday.HolidayRepository;

@Service
public class HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;

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
}