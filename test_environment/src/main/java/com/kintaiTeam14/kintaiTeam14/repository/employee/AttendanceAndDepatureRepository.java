package com.kintaiTeam14.kintaiTeam14.repository.employee;

import java.time.LocalDateTime;

public interface AttendanceAndDepatureRepository {

	void attendance(LocalDateTime time);
	
	void depature(LocalDateTime time);
}
