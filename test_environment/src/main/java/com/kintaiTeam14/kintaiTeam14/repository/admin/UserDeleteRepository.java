package com.kintaiTeam14.kintaiTeam14.repository.admin;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserDeleteRepository {

	private final JdbcTemplate jdbcTemplate;
	
	//employeeの削除
	public void deleteUser(String empId){
		this.deleteAttendance(empId);
		
		String sql_del="DELETE FROM employee WHERE employee_id=?";
		jdbcTemplate.update(sql_del, empId);
	}
	
	//attendanceの削除
	public void deleteAttendance(String empId) {
		String sql="SELECT attend_id FROM attendance WHERE employee_id=?";
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql, empId);
		this.deleteReason(list);
		
		String sql_del = "DELETE FROM attendance WHERE employee_id=?";
		jdbcTemplate.update(sql_del, empId);
	}
	
	//reasonの削除
	public void deleteReason(List<Map<String,Object>> list) {
		for(Map<String,Object> row:list) {
			String sql="DELETE FROM reason WHERE attend_id=?";
			String attend_id=row.get("attend_id").toString();
			jdbcTemplate.update(sql, attend_id);
		}
	}
}
