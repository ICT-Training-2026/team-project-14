package com.kintaiTeam14.kintaiTeam14.service.performance;

import java.util.List;

import com.kintaiTeam14.kintaiTeam14.entity.Performance;

public interface PerformanceService {
    List<Performance> getAllPerformances(Long userId);

	List<Performance> getAllPerformances1(Long userId);
}
