package com.kintaiTeam14.kintaiTeam14.service.attendance;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kintaiTeam14.kintaiTeam14.entity.Attendance;
import com.kintaiTeam14.kintaiTeam14.repository.attendance.AttendanceSyouninRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceSyouninService {
	private final AttendanceSyouninRepository attendanceRepository;
	
	public int updateAtClassificationService(Long employeeId, LocalDate date, Byte atClassification) {
	   return   attendanceRepository.updateAtClassification(employeeId, date, atClassification);
	}
	public List<String> findDatesByEmployeeIdAndAtClassificationService(Long employeeId, int atClassification) {
		 List<LocalDate> dateList = attendanceRepository.findDatesByEmployeeIdAndAtClassification(employeeId, atClassification);
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		    return dateList.stream()
		            .map(date -> date.format(formatter)) // "2024/07/10" のような形式に変換
		            .collect(Collectors.toList());
	}
	public List<Attendance> findAttendancesbyAtClassificationService( int atClassification, int atClassification2){
		List<Attendance> atendList=attendanceRepository.findAteAttendancesbyAtClassification(  atClassification,  atClassification2);
		return atendList;
	}
	public int changeAtClassificationByAttendIdService(Long attendId) {
		int sccese=0;
		int AtClassification =attendanceRepository.findAtClassificationbyAttendId(attendId);
		if(AtClassification==2) {
			attendanceRepository.changeAtClassificationByAttendId( attendId,4);
		} else {
			attendanceRepository.changeAtClassificationByAttendId( attendId,5);
		}
		
		
		return sccese;
		
	}
	
	public int changeAtClassificationByAttendIdRejectService(Long attendId) {
		int sccese=0;
	
		attendanceRepository.changeAtClassificationByAttendId( attendId,0);

		
		
		return sccese;
		
	}
		
	

}
