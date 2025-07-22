package com.kintaiTeam14.kintaiTeam14.service.performance;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kintaiTeam14.kintaiTeam14.entity.Performance;
import com.kintaiTeam14.kintaiTeam14.repository.performance.PerformanceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PerformanceServiceImpl implements PerformanceService {

    private final PerformanceRepository repository;

    @Override
    public List<Performance> getAllPerformances(Long userId) {
        return repository.findAll(userId);
    }

    @Override
    public List<Performance> getAllPerformances1(Long userId) {
        return null; // 既存メソッドに合わせて実装してください
    }

    @Override
    public boolean existsPerformancesForYear(Long userId, LocalDate startDate, LocalDate endDate) {
        return repository.existsByUserIdAndDateBetween(userId, startDate, endDate);
    }

    @Override
    @Transactional
    public void createPerformancesForYear(Long userId, LocalDate startDate, LocalDate endDate) {
        repository.createPerformancesForYear(userId, startDate, endDate);
    }
}
