// PerformanceServiceImpl.java
package com.kintaiTeam14.kintaiTeam14.service.performance;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kintaiTeam14.kintaiTeam14.entity.Performance;
import com.kintaiTeam14.kintaiTeam14.repository.HolidayRepository;
import com.kintaiTeam14.kintaiTeam14.repository.performance.PerformanceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PerformanceServiceImpl implements PerformanceService {

    private final PerformanceRepository repository;
    private final HolidayRepository holidayRepository;


    @Override
    public List<Performance> getAllPerformances(Long userId) {
        return repository.findAll(userId);
    }

    @Override
    public List<Performance> getAllPerformances1(Long userId) {
        return null; // 既存実装に合わせてください
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

	@Override
	public void updatePerformance(Performance performance) {
		repository.updatePerformance(performance);
	}

	@Override
    public List findByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return repository.findByUserIdAndDateRange(userId, startDate, endDate);
    }

    // 追加：祝日をDBから取得するメソッド
    @Override
    public Set findHolidaysBetween(LocalDate startDate, LocalDate endDate) {
        return holidayRepository.findByHolidayDateBetween(startDate, endDate)
                .stream()
                .map(holiday -> holiday.getHolidayDate())
                .collect(Collectors.toSet());
    }
}