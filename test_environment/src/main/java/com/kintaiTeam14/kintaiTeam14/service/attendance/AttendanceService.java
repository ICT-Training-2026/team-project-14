package com.kintaiTeam14.kintaiTeam14.service.attendance;

import java.util.List;

import com.kintaiTeam14.kintaiTeam14.entity.Attendance;

public interface AttendanceService {
    List<Attendance> findAll();
}