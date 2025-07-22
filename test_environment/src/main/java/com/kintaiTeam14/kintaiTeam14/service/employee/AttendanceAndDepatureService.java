package com.kintaiTeam14.kintaiTeam14.service.employee;

import java.time.LocalDateTime;

public interface AttendanceAndDepatureService {

	void attendance(LocalDateTime time);
	
	void depature(LocalDateTime time);
}
