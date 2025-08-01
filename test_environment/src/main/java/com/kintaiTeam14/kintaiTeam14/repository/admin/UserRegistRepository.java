package com.kintaiTeam14.kintaiTeam14.repository.admin;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.kintaiTeam14.kintaiTeam14.form.UserRegistForm;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRegistRepository {

	private final JdbcTemplate jdbcTemplate;
	private final PasswordEncoder passwordEncoder;
	
	public int userRegist(UserRegistForm f) {
		
		//0:成功 1:存在しない部署 2:ID重複
		int result=0;
		
		int emp_id = Integer.parseInt(f.getEmployeeId());
		
		//同じIDが登録されているかチェック
		String sql = "SELECT * FROM employee WHERE employee_id = ?";
		List<Map<String,Object>> employee = jdbcTemplate.queryForList(sql,emp_id);
		
		if(employee.isEmpty()) {
			
			if(checkDepartmentId(f.getDepartmentId())) {
				//新規ユーザーの初期パスは社員番号、is_passwordは1
				String sql_ins = "INSERT INTO employee (employee_id, department_id, employee_name, "
						+ "password, is_password, paid_holiday, comp_day) "
						+ "VALUES(?,?,?,?,?,?,?)";
				
				jdbcTemplate.update(sql_ins, emp_id, f.getDepartmentId(),
						f.getName(),passwordEncoder.encode(f.getEmployeeId()),1,0,0);
				System.out.println("社員番号：" + emp_id + " 社員名："+f.getName());
				System.out.println("登録しました");
				result=0;
			}
			else {
				result=1;
			}
		}
		else {
			result=2;
			System.out.println("すでにこのIDは登録されています");
		}
			
		return result;
	}
	
	//存在しない部署IDを弾くための関数
	public boolean checkDepartmentId(String id) {
		String sql = "SELECT department_id FROM departments WHERE department_id=?";

		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql, id);

		if(list.isEmpty()) {
			return false;
		}
		else {
			return true;
		}
	}
}
