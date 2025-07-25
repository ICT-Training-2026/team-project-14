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
	public String attendance(LocalDateTime time,Long emp_id) {
		return adr.attendance(time,emp_id);
	}

	@Override
	public String depature(LocalDateTime time,Long emp_id) {
		return adr.depature(time,emp_id);
	}

}
