package com.kintaiTeam14.kintaiTeam14.service.attendance;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.kintaiTeam14.kintaiTeam14.repository.attendance.AttendanceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceService {
	private final AttendanceRepository attendanceRepository;
	
	public int updateAtClassificationService(Long employeeId, LocalDate date, Byte atClassification) {
	   return   attendanceRepository.updateAtClassification(employeeId, date, atClassification);
	}

}
