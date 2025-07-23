package com.kintaiTeam14.kintaiTeam14.service.employee;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.kintaiTeam14.kintaiTeam14.repository.employee.AttendanceAndDepatureRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceAndDepatureServiceImpl implements AttendanceAndDepatureService {

	private final AttendanceAndDepatureRepository adr;
	
	@Override
	public void attendance(LocalDateTime time,Long emp_id) {
		adr.attendance(time,emp_id);
	}

	@Override
	public void depature(LocalDateTime time,Long emp_id) {
		adr.depature(time,emp_id);
	}

}
