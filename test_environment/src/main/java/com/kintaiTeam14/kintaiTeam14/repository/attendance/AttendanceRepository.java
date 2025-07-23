package com.kintaiTeam14.kintaiTeam14.repository.attendance;

import java.time.LocalDate;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class AttendanceRepository {
	private final JdbcTemplate jdbcTemplate;
	public int updateAtClassification(Long employeeId, LocalDate date, Byte atClassification) {
	    String sql = "UPDATE attendance SET at_classification = ? WHERE employee_id = ? AND date = ?";
	    return jdbcTemplate.update(sql, atClassification, employeeId, date);
	}


}
