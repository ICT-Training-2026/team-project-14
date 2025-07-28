package com.kintaiTeam14.kintaiTeam14.repository.batch;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EditTaskRepository {
	
	private final JdbcTemplate jdbcTemplate;

	public List<Map<String,Object>> checkExistView(Long empId){
		String sql="SELECT * FROM edit_tasks WHERE employee_id=?";
		return jdbcTemplate.queryForList(sql, empId);
	}
}
