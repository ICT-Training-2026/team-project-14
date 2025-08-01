package com.kintaiTeam14.kintaiTeam14.repository.employee;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kintaiTeam14.kintaiTeam14.service.performance.PerformanceService;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AttendanceAndDepatureRepositoryImpl implements AttendanceAndDepatureRepository {

	private final JdbcTemplate jdbcTemplate;
	private final PerformanceService s;
	
	//出勤処理
	@Override
	public String attendance(LocalDateTime time,Long emp_id) {
		
		String message="";
		
		LocalDate dateOnly = time.toLocalDate();
		
		String sql_search ="SELECT employee_id,arrival_time,date,end_time FROM attendance "
				+ "WHERE employee_id=? AND date=?";

		List<Map<String, Object>> today_arrival = jdbcTemplate.queryForList(sql_search, emp_id,dateOnly);
		
		//出退勤未登録時の処理
		if(today_arrival.get(0).get("arrival_time") == null && today_arrival.get(0).get("end_time") == null) {
			
			String sql_upd="UPDATE attendance SET arrival_time=? "
					+ "WHERE employee_id=? and date=?";
			
			
			jdbcTemplate.update(sql_upd,time,emp_id,dateOnly);
			
			System.out.println("社員番号 : "+emp_id.toString());
			System.out.println("出勤登録 : "+time);
			
			//休日出勤の場合、振休を1日増やす
			Set<LocalDate> holiday = s.findHolidaysBetween(dateOnly, dateOnly);
			if(!holiday.isEmpty()) {
				String sql_compDay = "UPDATE employee SET comp_day=comp_day+1 "
						+ "WHERE employee_id=?";
				jdbcTemplate.update(sql_compDay, emp_id);
				
				System.out.println("休日出勤のため振休を1日追加しました");
			}
			
			message="出勤ボタンが押されました。\n出勤時間： "+formatDate(time);
		}
		else {
			//退勤時間だけ登録されているときの出勤時間登録
			if(today_arrival.get(0).get("arrival_time") == null) {
				
				String sql_upd="UPDATE attendance SET arrival_time=? "
						+ "WHERE employee_id=? and date=?";
				
				jdbcTemplate.update(sql_upd,time,emp_id,dateOnly);
				
				System.out.println("社員番号 : "+emp_id.toString());
				System.out.println("出勤登録 : "+time);
				
				//休憩・残業時間の計算・登録(一応休憩・残業0で登録)
				breaktimeAndOvertime(emp_id, dateOnly);
				
				message="出勤ボタンが押されました。\n出勤時間： "+formatDate(time);
			}
			//すでに出勤時間が登録されているときの処理
			else {
				System.out.println("出勤時間登録済み");
				
				message="出勤時間は登録済みです";
			}
		}
		
		return message;
	}

	//退勤処理
	@Override
	public String depature(LocalDateTime time,Long emp_id) {
		
		String message="";
		
		LocalDate dateOnly = time.toLocalDate();
		
		String sql_search ="SELECT employee_id,arrival_time,end_time,date FROM attendance "
				+ "WHERE employee_id=? AND date=?";

		List<Map<String, Object>> today_arrival = jdbcTemplate.queryForList(sql_search, emp_id,dateOnly);
		
		//出退勤未登録時の処理
		if(today_arrival.get(0).get("arrival_time") == null && today_arrival.get(0).get("end_time") == null) {

			String sql_upd="UPDATE attendance SET end_time=? "
					+ "WHERE employee_id=? and date=?";


			jdbcTemplate.update(sql_upd,time,emp_id,dateOnly);

			System.out.println("社員番号 : "+emp_id.toString());
			System.out.println("退勤登録 : "+time);
			
			//休日出勤の場合、振休を1日増やす
			Set<LocalDate> holiday = s.findHolidaysBetween(dateOnly, dateOnly);
			if(!holiday.isEmpty()) {
				String sql_compDay = "UPDATE employee SET comp_day=comp_day+1 "
						+ "WHERE employee_id=?";
				jdbcTemplate.update(sql_compDay, emp_id);
				
				System.out.println("休日出勤のため振休を1日追加しました");
			}
			
			message="退勤ボタンが押されました。\n退勤時間： "+formatDate(time);
		}
		//今日の出勤が登録されているときの処理
		else {
			//出勤は登録されているが、退勤は登録されていないときの処理
			if(today_arrival.get(0).get("arrival_time") != null && today_arrival.get(0).get("end_time") == null){
				
				//---退勤時間の登録---
				String sql_upd="UPDATE attendance SET end_time=? "
						+ "WHERE employee_id=? and date=?";
				
				jdbcTemplate.update(sql_upd,time,emp_id,dateOnly);
				
				System.out.println("社員番号 : "+emp_id.toString());
				System.out.println("退勤登録 : "+time);
				//--------------------
				
				//休憩・残業時間の計算・登録
				breaktimeAndOvertime(emp_id, dateOnly);
				
				message="退勤ボタンが押されました。\n退勤時間： "+formatDate(time);
			}
			//出勤は押されてないけど、再度退勤が押されたとき
			else if(today_arrival.get(0).get("arrival_time") == null && today_arrival.get(0).get("end_time") != null) {
				
				String sql_upd="UPDATE attendance SET end_time=? "
						+ "WHERE employee_id=? and date=?";
				
				jdbcTemplate.update(sql_upd,time,emp_id,dateOnly);
				
				System.out.println("社員番号 : "+emp_id.toString());
				System.out.println("退勤修正 : "+time);
				
				message="退勤ボタンが再度押されました。\n退勤時間： "+formatDate(time);
			}
			//出勤も退勤も登録されているときの処理
			else {
				//現在dbに登録されている退勤時刻
				LocalDateTime currentEndTime = LocalDateTime.parse(today_arrival.get(0).get("end_time").toString());
				
				//dbに登録されている退勤時刻よりも、受け取った時刻があとの場合（退勤時間の更新）
				if(time.isAfter(currentEndTime)) {
					String sql_upd="UPDATE attendance SET end_time=? "
							+ "WHERE employee_id=? and date=?";
					
					jdbcTemplate.update(sql_upd,time,emp_id,dateOnly);
					
					System.out.println("社員番号 : "+emp_id.toString());
					System.out.println("退勤修正 : "+time);
					
					//休憩・残業時間の計算・登録
					breaktimeAndOvertime(emp_id, dateOnly);
					
					message="退勤ボタンが再度押されました。\n退勤時間： "+formatDate(time);
				}
			}
		}
		
		return message;
	}

	//休憩・残業時間の計算・登録をする関数
	@Override
	public void breaktimeAndOvertime(Long emp_id,LocalDate dateOnly) {
		
		String sql_search ="SELECT employee_id,arrival_time,end_time,date FROM attendance "
				+ "WHERE employee_id=? AND date=?";

		List<Map<String, Object>> today_arrival = jdbcTemplate.queryForList(sql_search, emp_id,dateOnly);
		LocalDateTime arrival = LocalDateTime.parse(today_arrival.get(0).get("arrival_time").toString());
		LocalDateTime end = LocalDateTime.parse(today_arrival.get(0).get("end_time").toString());
		// 差分をDurationとして取得
		Duration duration = Duration.between(arrival, end);

		int hours   = (int)duration.toHours();    // 時間単位の差分
		int minutes = (int)duration.toMinutes();  // 分単位の差分
		int break_time = 0;
		int overtime = 0;
		
		//4時間以上働いたら1時間の休憩
		if(hours>=4) {
			break_time=1;
		}
		//労働時間が8時間(休憩含む)を超えていたら残業（分単位で管理）
		if(hours>=8) {
			overtime=minutes-480;
		}
		
		String sql_break_over="UPDATE attendance SET break_time=?,overtime=? "
				+ "WHERE employee_id=? and date=?";
		
		jdbcTemplate.update(sql_break_over,break_time,overtime,emp_id,dateOnly);
		
		System.out.println("休憩時間 : "+break_time);
		System.out.println("残業時間 : "+overtime);
	}
	
	//LocalDateTime型を年/月/日 時:分:秒の文字列に変換する関数
	public String formatDate(LocalDateTime t) {
		
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

		return t.format(formatter);
	}
}
