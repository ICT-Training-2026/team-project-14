package com.kintaiTeam14.kintaiTeam14.repository.admin;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kintaiTeam14.kintaiTeam14.form.UserEditForm;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserEditRepository {

	private final JdbcTemplate jdbcTemplate;
	
	public int userEditCheck(UserEditForm f) {
		
		//0:成功 1:存在しない部署 2:何も編集されてない
		int result=0;
		
		String sql_currentInfo="SELECT employee_name,department_id,department_history FROM employee "
				+ "WHERE employee_id=?";
		List<Map<String,Object>> currentInfo=jdbcTemplate.queryForList(sql_currentInfo, f.getEmployeeId());	
		
		String currentName=currentInfo.get(0).get("employee_name").toString(); //現在の名前
		String currentDepartment=currentInfo.get(0).get("department_id").toString(); //現在の部署
		
		//存在しない部署IDが入力されていたら弾く
		result=checkDepartmentId(f.getDepartmentId());
		
		//なにも編集されていない場合
		if((currentName==null || currentName.equals(f.getName())) && (currentDepartment==null || currentDepartment.equals(f.getDepartmentId()))) {
			result=2;
		}
		
		return result;
	}
	
	public void editSchedule(UserEditForm f) {
		String sql="INSERT INTO edit_tasks (employee_id,new_employee_name,new_department_id,activate_time) "
				+ "VALUES (?,?,?,?) ";
		
		jdbcTemplate.update(sql, Integer.parseInt(f.getEmployeeId()),f.getName(),f.getDepartmentId(),f.getActivateDate());
	}
	
	//db登録実行
	public boolean userEditExe(UserEditForm f) {
		
		boolean result=true;
		
		String sql_currentInfo="SELECT employee_name,department_id,department_history FROM employee "
				+ "WHERE employee_id=?";
		List<Map<String,Object>> currentInfo=jdbcTemplate.queryForList(sql_currentInfo, f.getEmployeeId());	
		
		String currentName=currentInfo.get(0).get("employee_name").toString(); //現在の名前
		String currentDepartment=currentInfo.get(0).get("department_id").toString(); //現在の部署
		String currentDepartmentHistory; //部署移動履歴
		
		
		//部署変更がある場合のみ、部署の移動履歴を追加する
		if(!currentDepartment.equals(f.getDepartmentId())) {
			//部署移動履歴がなければ
			if(currentInfo.get(0).get("department_history")==null) {
				currentDepartmentHistory=currentDepartment;
			}
			//部署移動履歴があれば
			else {
				currentDepartmentHistory=currentInfo.get(0).get("department_history").toString()+currentDepartment;
			}
			
			String sql = "UPDATE employee SET department_id=?, employee_name=? ,department_history=? "
					+ "WHERE employee_id=?";
			
			jdbcTemplate.update(sql, f.getDepartmentId(),f.getName(),currentDepartmentHistory,Integer.parseInt(f.getEmployeeId()));
		}
		//名前の変更のみ行われる場合、部署の移動履歴は更新しない
		else if(!currentName.equals(f.getName()) && currentDepartment.equals(f.getDepartmentId())){
			String sql = "UPDATE employee SET employee_name=? WHERE employee_id=?";
			
			jdbcTemplate.update(sql, f.getName(), Integer.parseInt(f.getEmployeeId()));
		}
		//アホがなんも編集せずに編集登録したとき
		else {
			result=false;
		}
		
		return result;
	}
	
	//存在しない部署IDを弾くための関数
	public int checkDepartmentId(String id) {
		String sql = "SELECT department_id FROM departments WHERE department_id=?";
		
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql, id);
		
		if(list.isEmpty()) {
			return 1;
		}
		else {
			return 0;
		}
	}
}
