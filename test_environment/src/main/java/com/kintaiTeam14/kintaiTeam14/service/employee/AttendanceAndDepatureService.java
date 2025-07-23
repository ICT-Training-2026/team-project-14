package com.kintaiTeam14.kintaiTeam14.service.employee;

import java.time.LocalDateTime;

public interface AttendanceAndDepatureService {

	String attendance(LocalDateTime time,Long emp_id);
	
	String depature(LocalDateTime time,Long emp_id);
}
