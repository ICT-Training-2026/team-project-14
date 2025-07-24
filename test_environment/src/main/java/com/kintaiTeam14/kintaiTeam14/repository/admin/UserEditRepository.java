package com.kintaiTeam14.kintaiTeam14.repository.admin;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kintaiTeam14.kintaiTeam14.form.UserEditForm;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserEditRepository {

	private final JdbcTemplate jdbcTemplate;
	
	public void userEdit(UserEditForm f) {
		
		String sql = "UPDATE employee SET department_id=?, employee_name=? "
				+ "WHERE employee_id=?";
		
		jdbcTemplate.update(sql, f.getDepartmentId(),f.getName(),Integer.parseInt(f.getEmployeeId()));
		
		System.out.println("仮　編集完了");
	}
}
