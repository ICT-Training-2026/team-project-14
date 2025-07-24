package com.kintaiTeam14.kintaiTeam14.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.kintaiTeam14.kintaiTeam14.entity.Attendance;
import com.kintaiTeam14.kintaiTeam14.service.attendance.AttendanceService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminApprovalkyuukaController {
	private final AttendanceService attendanceService;
	@PostMapping("/admin/approval-correction/approval-kyuuka")
	public String approvalKyuuka(Model m) {
		List<Attendance> results = attendanceService.findAttendancesbyAtClassificationService(2,3);
		System.out.println(results);
		m.addAttribute("attendances", results); 
		
		
		
		return "admin/approval-kyuuka";
	}
	

	@PostMapping("/admin/approval-correction/approval-kyuuka/reject/{attendId}")
	public ResponseEntity<?> approvalKyuukaReject(Model m,@PathVariable Long attendId){
		int scccese=attendanceService.changeAtClassificationByAttendIdRejectService(attendId);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/admin/approval-correction/approval-kyuuka/approve/{attendId}")
	public ResponseEntity<?> approvalKyuukaApprove(Model m,@PathVariable Long attendId) {
		int scccese=attendanceService.changeAtClassificationByAttendIdService(attendId);
		
		return ResponseEntity.ok().build();
	}
	
	

}
