package com.kintaiTeam14.kintaiTeam14.repository.performance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.kintaiTeam14.kintaiTeam14.entity.Performance;
import com.kintaiTeam14.kintaiTeam14.entity.RePerformance;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PerformanceRepositoryImpl implements PerformanceRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Performance> findAll(Long userId) {
        String sql = "SELECT a.attend_id, date, arrival_time, end_time, break_time, status, reason " +
                     "FROM attendance a LEFT JOIN reason r ON a.attend_id = r.attend_id " +
                     "WHERE a.employee_id = ?";

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
		System.out.println(performance.getReason());


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
        String sqlReasonCheck = "SELECT COUNT(1) FROM reason WHERE attend_id = ?";
        Integer count = jdbcTemplate.queryForObject(sqlReasonCheck, Integer.class, performance.getId());

        if (count != null && count > 0) {
            // レコードが存在するならUPDATE
            String sqlReasonUpdate = "UPDATE reason SET reason = ? WHERE attend_id = ?";
            jdbcTemplate.update(sqlReasonUpdate, performance.getReason(), performance.getId());
        } else {
            // レコードがなければINSERT
            String sqlReasonInsert = "INSERT INTO reason (reason_id, attend_id, reason) VALUES (0,?, ?)";
            jdbcTemplate.update(sqlReasonInsert, performance.getId(), performance.getReason());
        }
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
    public void createPerformancesForYear(Long userId, LocalDate startDate, LocalDate endDate,Set<LocalDate>holidays) {
        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            if (!existsByUserIdAndDate(userId, date)) {
            	if (holidays.contains(date)) {
            		 createAttendanceWithReason(userId, date,0);
                } else {
                	createAttendanceWithReason(userId, date,1);
                }
            }
            date = date.plusDays(1);
        }
    }

    @Override
    public void createAttendanceWithReason(Long userId, LocalDate date, int atClassification) {
        String insertAttendanceSql = "INSERT INTO attendance (employee_id, date, status, at_classification, break_time, overtime) VALUES (?, ?, '未申請', ?, 1, 0)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertAttendanceSql, new String[] {"attend_id"});
            ps.setLong(1, userId);
            ps.setObject(2, date);
            ps.setInt(3, atClassification);
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
    public List<RePerformance> findByreId(int reId) {
        String sql = "SELECT a.attend_id, a.date, a.break_time, a.at_classification, a.arrival_time, a.end_time, a.status, r.reason, r.correctReason, r.diffReason " +
             "FROM attendance a LEFT JOIN reason r ON a.attend_id = r.attend_id " +
             "WHERE a.attend_id = ?";


        // jdbcTemplateを使ってSQLを実行し、結果をRePerformanceオブジェクトにマッピングしてリストで返す
        return jdbcTemplate.query(sql, new Object[]{reId}, (rs, rowNum) -> {
            RePerformance rp = new RePerformance();

            // attend_idをLong型でセット
            rp.setId(rs.getLong("attend_id"));

            // dateカラムをLocalDateに変換してセット
            java.sql.Date sqlDate = rs.getDate("date");
            if (sqlDate != null) {
                rp.setDate(sqlDate.toLocalDate());

                // 曜日を取得してセット（日本語の狭い形式）
                String dayOfWeek = sqlDate.toLocalDate()
                    .getDayOfWeek()
                    .getDisplayName(java.time.format.TextStyle.NARROW, java.util.Locale.JAPANESE);
                rp.setDayOfWeek(dayOfWeek);
            }

            // arrival_timeをLocalTimeに変換してセット
            java.sql.Time sqlStartTime = rs.getTime("arrival_time");
            rp.setStartTime(sqlStartTime != null ? sqlStartTime.toLocalTime() : null);

            // end_timeをLocalTimeに変換してセット
            java.sql.Time sqlEndTime = rs.getTime("end_time");
            rp.setEndTime(sqlEndTime != null ? sqlEndTime.toLocalTime() : null);
            rp.setBreakTime(rs.getInt("break_time"));

            // statusをセット
            rp.setStatus(rs.getString("status"));
            rp.setAtClassification(rs.getInt("at_classification"));
            rp.setReason(rs.getString("reason"));
            rp.setCorrectReason(rs.getString("correctReason"));
            rp.setDiffReason(rs.getString("diffReason"));
            // // at_classificationをセット
            // rp.setAtClassification(rs.getInt("at_classification"));

            // // correct_reasonをセット
            // rp.setCorrectReason(rs.getString("correct_reason"));

            // // diff_reasonをセット
            // rp.setDiffReason(rs.getString("diff_reason"));

            return rp;
        });

    }


    @Override
    public void updateRePerformance(RePerformance reperformance) {
        String sqlAttendance = "UPDATE attendance SET " +
                "arrival_time = ?, " +
                "end_time = ?, " +
                "break_time = ?, " +
                "status = ? " +   // 最後のカラムの後にカンマは不要
                "WHERE attend_id = ?";

        java.sql.Timestamp startTimestamp = null;
        java.sql.Timestamp endTimestamp = null;

        if (reperformance.getDate() != null && reperformance.getStartTime() != null) {
            startTimestamp = java.sql.Timestamp.valueOf(
                    reperformance.getDate().atTime(reperformance.getStartTime()));
        }

        if (reperformance.getDate() != null && reperformance.getEndTime() != null) {
            endTimestamp = java.sql.Timestamp.valueOf(
                    reperformance.getDate().atTime(reperformance.getEndTime()));
        }

        jdbcTemplate.update(sqlAttendance,
                startTimestamp,
                endTimestamp,
                reperformance.getBreakTime(),
                reperformance.getStatus(),
                reperformance.getId());

        String sqlReason = "UPDATE reason SET " +
                "reason = ?, " +
                "correctReason = ?, " +
                "diffReason = ? " +  // ここも最後のカラムなのでカンマは不要
                "WHERE attend_id = ?";


        jdbcTemplate.update(sqlReason,
                reperformance.getReason(),
                reperformance.getCorrectReason(),
                reperformance.getDiffReason(),
                reperformance.getId());
    }
}