package com.kintaiTeam14.kintaiTeam14.repository.performance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kintaiTeam14.kintaiTeam14.entity.Performance;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PerformanceRepositoryImpl implements PerformanceRepository {

    private final JdbcTemplate jdbcTemplate;

    @SuppressWarnings("deprecation")
	@Override
    public List<Performance> findAll(Long userId) {
    String sql = "SELECT date, arrival_time, end_time, break_time, status, reason " +
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

            final LocalTime start_time = rs.getTime("arrival_time").toLocalTime();
            s.setStartTime(start_time);

            final LocalTime end_time = rs.getTime("end_time").toLocalTime();
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
}
