package com.example.demo.service.schedule;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Schedule;
import com.example.demo.repository.schedule.ScheduleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
public class PerformanceServiceImpl implements PerformanceService {

    private final PerformanceRepository repository;

    @Override
    public List<Performance> getAllPerformances() {
        List<Performance> list = repository.findAll();
        return list;
    }
}
