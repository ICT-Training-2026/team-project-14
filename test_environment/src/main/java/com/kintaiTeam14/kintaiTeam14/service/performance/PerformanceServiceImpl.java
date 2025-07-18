package com.kintaiTeam14.kintaiTeam14.service.performance;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kintaiTeam14.kintaiTeam14.entity.Performance;
import com.kintaiTeam14.kintaiTeam14.repository.performance.PerformanceRepository;

import lombok.RequiredArgsConstructor;

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
