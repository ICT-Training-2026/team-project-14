package com.kintaiTeam14.kintaiTeam14.repository.attendance;

import java.time.LocalDate;
import java.util.List;

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
	public List<java.time.LocalDate> findDatesByEmployeeIdAndAtClassification(Long employeeId, int atClassification) {
        String sql = "SELECT date FROM attendance WHERE employee_id = ? AND at_classification = ?";
        return jdbcTemplate.query(
            sql,
            new Object[]{employeeId, atClassification},
            (rs, rowNum) -> rs.getDate("date").toLocalDate()
        );
    }


}
