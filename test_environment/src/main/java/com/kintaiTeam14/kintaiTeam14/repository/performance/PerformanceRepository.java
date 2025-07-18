package com.kintaiTeam14.kintaiTeam14.repository.performance;

import java.util.List;

import com.kintaiTeam14.kintaiTeam14.entity.Performance;

public interface PerformanceRepository {
    List<Performance> findAll();
}
