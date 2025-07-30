package com.kintaiTeam14.kintaiTeam14.service.attendance;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kintaiTeam14.kintaiTeam14.entity.Attendance;
import com.kintaiTeam14.kintaiTeam14.repository.attendance.AttendanceSyouninRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceSyouninService {
	private final AttendanceSyouninRepository attendanceRepository;
	
	public int updateAtClassificationService(Long employeeId, LocalDate date, Byte atClassification,String status) {
	
	   return   attendanceRepository.updateAtClassification(employeeId, date, atClassification, status);
	}
	public List<LocalDate> findDatesByEmployeeIdAndAtClassificationService(Long employeeId, int atClassification) {
		return attendanceRepository.findDatesByEmployeeIdAndAtClassification(employeeId, atClassification);
		    
	}
	public List<Attendance> findAttendancesbyAtClassificationService( int atClassification, int atClassification2){
		List<Attendance> atendList=attendanceRepository.findAteAttendancesbyAtClassification(  atClassification,  atClassification2);
		return atendList;
	}
	public int changeAtClassificationByAttendIdService(Long attendId) {
		int sccese=0;
		int AtClassification =attendanceRepository.findAtClassificationbyAttendId(attendId);
		if(AtClassification==2) {
			sccese=attendanceRepository.changeAtClassificationByAttendId( attendId,4,"年休");
		} else {
			sccese=attendanceRepository.changeAtClassificationByAttendId( attendId,5,"振休");
		}
		
		
		return sccese;
		
	}
	
	public int changeAtClassificationByAttendIdRejectService(Long attendId) {
		int sccese=0;
	
		attendanceRepository.changeAtClassificationByAttendId( attendId,0,"未申請");

		
		
		return sccese;
		
	}
	
	public int setAttendTimebyAttendIdService(Long attendId) {
		int success=0;
		success=attendanceRepository.setAttendTimebyAttendId(attendId);
		return success;
		
		
	}
	public int findAtClassificationbyAttendIdService(Long attendId) {
		int AtClassification =attendanceRepository.findAtClassificationbyAttendId(attendId);
		return AtClassification;
	}
		
	

}
