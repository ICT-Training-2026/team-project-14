// PerformanceRepositoryImpl.java
package com.kintaiTeam14.kintaiTeam14.repository.performance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.kintaiTeam14.kintaiTeam14.entity.Performance;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PerformanceRepositoryImpl implements PerformanceRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Performance> findAll(Long userId) {
        String sql = "SELECT a.attend_id, date, arrival_time, end_time, break_time, status, reason " +
                     "FROM attendance a LEFT JOIN reason r ON a.attend_id = r.attend_id " +
                     "WHERE a.employee_id = ? AND EXTRACT(YEAR FROM a.date) = 2025 AND EXTRACT(MONTH FROM a.date) = 7";

        return jdbcTemplate.query(sql, new Object[]{userId}, new RowMapper<Performance>() {
            @Override
            public Performance mapRow(final ResultSet rs, final int rowNum) throws SQLException {
                final Performance s = new Performance();

                final LocalDate date = rs.getDate("date").toLocalDate();
                s.setDate(date);

                final String dayOfweek = date.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.JAPANESE);
                s.setDayOfWeek(dayOfweek);

                final int id = rs.getInt("attend_id");
                s.setId((long) id);

                final LocalTime start_time = rs.getTime("arrival_time") != null ? rs.getTime("arrival_time").toLocalTime() : null;
                s.setStartTime(start_time);

                final LocalTime end_time = rs.getTime("end_time") != null ? rs.getTime("end_time").toLocalTime() : null;
                s.setEndTime(end_time);

                final int break_time = rs.getInt("break_time");
                s.setBreakTime(break_time);

                final String status = rs.getString("status");
                s.setStatus(status);

                final String reason = rs.getString("reason");
                s.setReason(reason);

                return s;
            }
        });
    }

    @Override
    public void updatePerformance(Performance performance) {
		System.out.println("AAAAA");
		System.out.println(performance.getId());
		System.out.println(performance.getDayOfWeek());
		System.out.println(performance.getBreakTime());
		System.out.println(performance.getStatus());

		String sqlAttendance = "UPDATE attendance SET " +
	            "arrival_time = ?, " +
	            "end_time = ?, " +
	            "break_time = ?, " +
	            "status = ? " +
	            "WHERE attend_id = ?";

	    java.sql.Timestamp startTimestamp = null;
	    java.sql.Timestamp endTimestamp = null;

	    if (performance.getDate() != null && performance.getStartTime() != null) {
	        startTimestamp = java.sql.Timestamp.valueOf(
	                performance.getDate().atTime(performance.getStartTime()));
	    }

	    if (performance.getDate() != null && performance.getEndTime() != null) {
	        endTimestamp = java.sql.Timestamp.valueOf(
	                performance.getDate().atTime(performance.getEndTime()));
	    }

	    jdbcTemplate.update(sqlAttendance,
	            startTimestamp,
	            endTimestamp,
	            performance.getBreakTime(),
	            performance.getStatus(),
	            performance.getId());

//        // 2) reasonテーブルのUPDATEまたはINSERT
//        String sqlReasonCheck = "SELECT COUNT(1) FROM reason WHERE attend_id = ?";
//        Integer count = jdbcTemplate.queryForObject(sqlReasonCheck, Integer.class, performance.getId());
//
//        if (count != null && count > 0) {
//            // レコードが存在するならUPDATE
//            String sqlReasonUpdate = "UPDATE reason SET reason = ? WHERE attend_id = ?";
//            jdbcTemplate.update(sqlReasonUpdate, performance.getReason(), performance.getId());
//        } else {
//            // レコードがなければINSERT
//            String sqlReasonInsert = "INSERT INTO reason (attend_id, reason) VALUES (?, ?)";
//            jdbcTemplate.update(sqlReasonInsert, performance.getId(), performance.getReason());
//        }
    }

    @Override
    public boolean existsByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT EXISTS (SELECT 1 FROM attendance WHERE employee_id = ? AND date BETWEEN ? AND ?)";
        Boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class, userId, startDate, endDate);
        return exists != null && exists;
    }

    @Override
    public boolean existsByUserIdAndDate(Long userId, LocalDate date) {
        String sql = "SELECT EXISTS (SELECT 1 FROM attendance WHERE employee_id = ? AND date = ?)";
        Boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class, userId, date);
        return exists != null && exists;
    }

    @Override
    public void createPerformancesForYear(Long userId, LocalDate startDate, LocalDate endDate) {
        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            if (!existsByUserIdAndDate(userId, date)) {
                String insertSql = "INSERT INTO attendance (employee_id, date, status, at_classification, break_time, overtime) " +
                                   "VALUES (?, ?, '未申請', 0, 0, 0)";
                jdbcTemplate.update(insertSql, userId, date);
            }
            date = date.plusDays(1);
        }
    }

    @Override
    public void createAttendanceWithReason(Long userId, LocalDate date) {
        String insertAttendanceSql = "INSERT INTO attendance (employee_id, date, status, at_classification, break_time, overtime) VALUES (?, ?, '未申請', 0, 1, 0)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertAttendanceSql, new String[] {"attend_id"});
            ps.setLong(1, userId);
            ps.setObject(2, date);
            return ps;
        }, keyHolder);

        Long attendId = keyHolder.getKey().longValue();

        String insertReasonSql = "INSERT INTO reason (attend_id, reason, reason_id) VALUES (?, '', 0)";
        jdbcTemplate.update(insertReasonSql, attendId);
    }

    @Override
    public List<Performance> findByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT a.attend_id, date, arrival_time, end_time, break_time, status, reason " +
                     "FROM attendance a LEFT JOIN reason r ON a.attend_id = r.attend_id " +
                     "WHERE a.employee_id = ? AND a.date BETWEEN ? AND ? " +
                     "ORDER BY a.date";

        return jdbcTemplate.query(sql, new Object[]{userId, startDate, endDate}, new RowMapper<Performance>() {
            @Override
            public Performance mapRow(final ResultSet rs, final int rowNum) throws SQLException {
                final Performance s = new Performance();

                final LocalDate date = rs.getDate("date").toLocalDate();
                s.setDate(date);

                final int id = rs.getInt("attend_id");
                s.setId((long) id);

                final String dayOfweek = date.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.JAPANESE);
                s.setDayOfWeek(dayOfweek);

                final LocalTime start_time = rs.getTime("arrival_time") != null ? rs.getTime("arrival_time").toLocalTime() : null;
                s.setStartTime(start_time);

                final LocalTime end_time = rs.getTime("end_time") != null ? rs.getTime("end_time").toLocalTime() : null;
                s.setEndTime(end_time);

                final int break_time = rs.getInt("break_time");
                s.setBreakTime(break_time);

                final String status = rs.getString("status");
                s.setStatus(status);

                final String reason = rs.getString("reason");
                s.setReason(reason);

                return s;
            }
        });
    }
	@Override
	public void updatePerformance(Performance performance) {
	    String sql = "UPDATE performances SET " +
	            "day_of_week = ?, " +
	            "performance_date = ?, " +
	            "start_time = ?, " +
	            "end_time = ?, " +
	            "break_time = ?, " +
	            "status = ?, " +
	            "reason = ? " +
	            "WHERE id = ?";

	    jdbcTemplate.update(sql,
	            performance.getDayOfWeek(),
	            java.sql.Date.valueOf(performance.getDate()),
	            java.sql.Time.valueOf(performance.getStartTime()),
	            java.sql.Time.valueOf(performance.getEndTime()),
	            performance.getBreakTime(),
	            performance.getStatus(),
	            performance.getReason(),
	            performance.getId());
	}
}