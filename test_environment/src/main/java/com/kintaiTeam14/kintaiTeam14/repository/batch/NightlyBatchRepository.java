package com.kintaiTeam14.kintaiTeam14.repository.batch;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NightlyBatchRepository {

	private final JdbcTemplate jdbcTemplate;
	
	public List<Map<String,Object>> checkTask(){
		String sql="SELECT * FROM edit_tasks WHERE activate_time < ?";
		LocalDateTime now = LocalDateTime.now();
		return jdbcTemplate.queryForList(sql,now);
	}
	
	public void deleteTask(String task_id) {
		String sql="DELETE FROM edit_tasks WHERE task_id=?";
		jdbcTemplate.update(sql, Integer.parseInt(task_id));
	}
}
