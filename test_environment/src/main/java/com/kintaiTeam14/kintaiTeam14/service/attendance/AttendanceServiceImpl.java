package com.kintaiTeam14.kintaiTeam14.service.attendance;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kintaiTeam14.kintaiTeam14.entity.Attendance;
import com.kintaiTeam14.kintaiTeam14.repository.attendance.AttendanceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;

    @Override
    public List<Attendance> findAll() {
        return attendanceRepository.findAll();
    }
}