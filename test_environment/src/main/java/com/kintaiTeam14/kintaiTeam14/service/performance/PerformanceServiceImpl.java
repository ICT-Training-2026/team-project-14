// PerformanceServiceImpl.java
package com.kintaiTeam14.kintaiTeam14.service.performance;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kintaiTeam14.kintaiTeam14.entity.Performance;
import com.kintaiTeam14.kintaiTeam14.entity.RePerformance;
import com.kintaiTeam14.kintaiTeam14.repository.performance.PerformanceHolidayRepository;
import com.kintaiTeam14.kintaiTeam14.repository.performance.PerformanceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PerformanceServiceImpl implements PerformanceService {

    private final PerformanceRepository repository;
    private final PerformanceHolidayRepository holidayRepository;

    @Override
	public void updatePerformance(Performance performance) {
		repository.updatePerformance(performance);
	}

    @Override
	public void updateRePerformance(RePerformance reperformance) {
		repository.updateRePerformance(reperformance);
	}

    @Override
    public List<Performance> getAllPerformances(Long userId) {
        return repository.findAll(userId);
    }

    @Override
    public List<Performance> getAllPerformances1(Long userId) {
        // 必要に応じて実装
        return null;

    }

    @Override
    public boolean existsPerformancesForYear(Long userId, LocalDate startDate, LocalDate endDate) {
        return repository.existsByUserIdAndDateBetween(userId, startDate, endDate);
    }

    @Override
    @Transactional
    public void createPerformancesForYear(Long userId, LocalDate startDate, LocalDate endDate) {
    	Set<LocalDate>holidays=findHolidaysBetween(startDate, endDate);
    	repository.createPerformancesForYear(userId, startDate, endDate,holidays);
    }

//	@Override
//	public void updatePerformance(Performance performance) {
//		repository.updatePerformance(performance);
//
//	}
//}
//
//    }

    @Override
    public List<Performance> findByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return repository.findByUserIdAndDateRange(userId, startDate, endDate);
    }

    // 祝日と土日を含めて休日を取得するメソッド
    @Override
    public Set<LocalDate> findHolidaysBetween(LocalDate startDate, LocalDate endDate) {
        Set<LocalDate> holidays = holidayRepository.findByHolidayDateBetween(startDate, endDate)
                .stream()
                .map(holiday -> holiday.getHolidayDate())
                .collect(Collectors.toSet());

        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            switch (date.getDayOfWeek()) {
                case SATURDAY:
                case SUNDAY:
                    holidays.add(date);
                    break;
                default:
                    break;
            }
            date = date.plusDays(1);
        }
        return holidays;
    }

    // 月間所定労働時間(h)を計算するメソッド
    @Override
    public double calculateScheduledWorkHours(Long userId, LocalDate startDate, LocalDate endDate) {
        Set<LocalDate> holidays = findHolidaysBetween(startDate, endDate);
        long totalDays = startDate.datesUntil(endDate.plusDays(1)).count();
        long workDays = totalDays - holidays.size();
        return workDays * 7.0;
    }

    // 実労働時間(h)を計算するメソッド
    @Override
    public double calculateActualWorkHours(Long userId, LocalDate startDate, LocalDate endDate) {
        List<Performance> performances = findByUserIdAndDateRange(userId, startDate, endDate);
        double totalHours = 0.0;
        for (Performance p : performances) {
            if (p.getStartTime() != null && p.getEndTime() != null) {
                long minutes = java.time.Duration.between(p.getStartTime(), p.getEndTime()).toMinutes();
                totalHours += minutes / 60.0;
            }
        }
        return totalHours;
    }

    // 残業時間(h)を計算するメソッド
    @Override
    public double calculateOvertimeHours(Long userId, LocalDate startDate, LocalDate endDate) {
        double actual = calculateActualWorkHours(userId, startDate, endDate);
        double scheduled = calculateScheduledWorkHours(userId, startDate, endDate);
        double overtime = actual - scheduled;
        return Math.max(overtime, 0.0);
    }

    // 残年休日数をDBから取得するメソッド（仮実装）
    @Override
    public int getRemainingPaidHoliday(Long userId) {
        // TODO: employeeテーブルからpaid_holidayを取得する実装を行う
        return 0;
    }

    // 残振休日数をDBから取得するメソッド（仮実装）
    @Override
    public int getRemainingCompDay(Long userId) {
        // TODO: employeeテーブルからcomp_dayを取得する実装を行う
        return 0;
    }

	@Override
	public List<RePerformance> findByreId(int reId) {
		return repository.findByreId(reId);
	}
}