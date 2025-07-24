package com.kintaiTeam14.kintaiTeam14.repository.admin;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kintaiTeam14.kintaiTeam14.form.UserRegistForm;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRegistRepository {

	private final JdbcTemplate jdbcTemplate;
	
	public boolean userRegist(UserRegistForm f) {
		
		boolean result;
		
		int emp_id = Integer.parseInt(f.getEmployeeId());
		
		//同じIDが登録されているかチェック
		String sql = "SELECT * FROM employee WHERE employee_id = ?";
		List<Map<String,Object>> employee = jdbcTemplate.queryForList(sql,emp_id);
		
		if(employee.isEmpty()) {
			
			//新規ユーザーの初期パスは社員番号、is_passwordは1
			String sql_ins = "INSERT INTO employee (employee_id, department_id, employee_name, "
					+ "password, is_password, paid_holiday, comp_day) "
					+ "VALUES(?,?,?,?,?,?,?)";
			
			jdbcTemplate.update(sql_ins, emp_id, f.getDepartmentId(),
					f.getName(),f.getEmployeeId(),1,0,0);
			System.out.println("社員番号：" + emp_id + " 社員名："+f.getName());
			System.out.println("登録しました");
			result=true;
		}
		else {
			result=false;
			System.out.println("すでにこのIDは登録されています");
		}
			
		
		return result;
	}
}
