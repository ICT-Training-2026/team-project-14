package com.kintaiTeam14.kintaiTeam14.service.attendance;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kintaiTeam14.kintaiTeam14.entity.Attendance;
import com.kintaiTeam14.kintaiTeam14.repository.attendance.AttendanceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceService {
	private final AttendanceRepository attendanceRepository;
	
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
		
	

}
