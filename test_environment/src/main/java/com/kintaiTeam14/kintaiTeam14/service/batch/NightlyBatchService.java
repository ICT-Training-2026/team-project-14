package com.kintaiTeam14.kintaiTeam14.service.batch;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.kintaiTeam14.kintaiTeam14.form.UserEditForm;
import com.kintaiTeam14.kintaiTeam14.repository.admin.UserDeleteRepository;
import com.kintaiTeam14.kintaiTeam14.repository.admin.UserEditRepository;
import com.kintaiTeam14.kintaiTeam14.repository.batch.NightlyBatchRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NightlyBatchService {
	
	private final NightlyBatchRepository nr;
	private final UserEditRepository ur;
	private final UserDeleteRepository udr;

	@Scheduled(cron = "0 0 2 * * *", zone = "Asia/Tokyo")
	public void checkTask() {
		
		List<Map<String,Object>> list = nr.checkEditTask();
		System.out.println("タスクリスト");
		System.out.println(list);
		
		for(Map<String,Object> row:list) {
			UserEditForm f = new UserEditForm();
			f.setEmployeeId(row.get("employee_id").toString());
			f.setName(row.get("new_employee_name").toString());
			f.setDepartmentId(row.get("new_department_id").toString());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	        LocalDateTime datetime = LocalDateTime.parse(row.get("activate_time").toString(), formatter);
	        LocalDate date = datetime.toLocalDate();
			f.setActivateDate(date);
			
			ur.userEditExe(f);
			nr.deleteTask(row.get("task_id").toString());
		}
		
		
		
	}
	
	@Scheduled(cron = "0 0 2 1 * *", zone = "Asia/Tokyo")
	public void checkDelete() {
		List<Map<String,Object>> list = nr.checkDeleteTask();

		for(Map<String,Object> row:list) {
			String empId=row.get("employee_id").toString();
			udr.deleteUser(empId);
		}

	}
}
