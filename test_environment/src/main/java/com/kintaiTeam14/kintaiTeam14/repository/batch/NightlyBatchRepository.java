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
	
	public List<Map<String,Object>> checkEditTask(){
		String sql="SELECT * FROM edit_tasks WHERE activate_time < ?";
		LocalDateTime now = LocalDateTime.now();
		return jdbcTemplate.queryForList(sql,now);
	}
	
	public void deleteTask(String task_id) {
		String sql="DELETE FROM edit_tasks WHERE task_id=?";
		jdbcTemplate.update(sql, Integer.parseInt(task_id));
	}
	
	public List<Map<String,Object>> checkDeleteTask(){
		String sql="SELECT * FROM employee WHERE isdelete=1 and updated_at < ?";
        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(5);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql, oneYearAgo);
        System.out.println("ユーザ削除実行リスト");
        System.out.println(list);
        return list;
	}
	
	public void addPaidHoliday() {
		
		String sql="SELECT employee_id FROM employee WHERE NOT isdelete=1";
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
		
		String sql_add="UPDATE employee SET paid_holiday=paid_holiday+20 WHERE employee_id=?";
		for(Map<String,Object> row:list) {
			jdbcTemplate.update(sql_add, row.get("employee_id").toString());
		}
		
	}
}
