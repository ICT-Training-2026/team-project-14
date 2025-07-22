package com.kintaiTeam14.kintaiTeam14.repository.employee;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

@Repository
public class AttendanceAndDepatureRepositoryImpl implements AttendanceAndDepatureRepository {

	@Override
	public void attendance(LocalDateTime time) {
		String sql="SELECT "
				+ "";
	}

	@Override
	public void depature(LocalDateTime time) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
