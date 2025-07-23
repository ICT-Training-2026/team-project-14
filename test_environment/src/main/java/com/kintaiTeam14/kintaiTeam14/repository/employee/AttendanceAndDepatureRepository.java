package com.kintaiTeam14.kintaiTeam14.repository.employee;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface AttendanceAndDepatureRepository {

	//
	void attendance(LocalDateTime time,Long emp_id);
	
	void depature(LocalDateTime time,Long emp_id);
	
	void breaktimeAndOvertime(Long emp_id,LocalDate dateOnly);
}
