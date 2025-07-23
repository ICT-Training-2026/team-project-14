package com.kintaiTeam14.kintaiTeam14.service.employee;

import java.time.LocalDateTime;

public interface AttendanceAndDepatureService {

	void attendance(LocalDateTime time,Long emp_id);
	
	void depature(LocalDateTime time,Long emp_id);
}
