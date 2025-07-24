package com.kintaiTeam14.kintaiTeam14.repository.attendance;

import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kintaiTeam14.kintaiTeam14.entity.Attendance;

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
	
	public int findAtClassificationbyAttendId(Long attendId) {
		 String sql = "SELECT at_classification FROM attendance WHERE attend_id = ?";
		    // queryForObjectの第3引数にint.classを指定
		    return jdbcTemplate.queryForObject(sql, new Object[]{attendId}, int.class);
		
		
	}
	
	
	public void changeAtClassificationByAttendId(Long attendId,int at_classification) {
		String sql = "UPDATE attendance SET at_classification = ? WHERE attend_id = ?";
	    jdbcTemplate.update(sql, at_classification, attendId);
		
	}
	
	public List<Attendance>findAteAttendancesbyAtClassification( int atClassification, int atClassification2){
		 String sql = "SELECT attend_id, employee_id, arrival_time, end_time, break_time, at_classification, overtime, status, created_at, updated_at, date "
                 + "FROM attendance "
                 + "WHERE at_classification IN (?, ?)";

      return jdbcTemplate.query(
          sql,
          new Object[]{atClassification, atClassification2},
          (rs, rowNum) -> {
              Attendance attendance = new Attendance();
              attendance.setAttendId(rs.getLong("attend_id"));
              attendance.setEmployeeId(rs.getInt("employee_id"));
              attendance.setArrivalTime(rs.getTimestamp("arrival_time") != null ? rs.getTimestamp("arrival_time").toLocalDateTime() : null);
              attendance.setEndTime(rs.getTimestamp("end_time") != null ? rs.getTimestamp("end_time").toLocalDateTime() : null);
              attendance.setBreakTime(rs.getByte("break_time"));
              attendance.setAtClassification(rs.getByte("at_classification")); // Byte型推奨
              attendance.setOvertime(rs.getByte("overtime"));
              attendance.setStatus(rs.getString("status"));
              attendance.setCreatedAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
              attendance.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
              attendance.setDate(rs.getDate("date").toLocalDate());
              return attendance;
          }
      );
	}


}
