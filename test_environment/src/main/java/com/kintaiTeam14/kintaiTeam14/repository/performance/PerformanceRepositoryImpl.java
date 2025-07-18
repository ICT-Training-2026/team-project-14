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

    @Override
    public List<Performance> findAll() {
        String sql = "SELECT date,arrival_time,end_time,break_time,status FROM attendance";
        return jdbcTemplate.query(sql, new RowMapper<Performance>() {
            @Override
            public Performance mapRow(ResultSet rs, int rowNum) throws SQLException {
                Performance s = new Performance();
                LocalDate date = rs.getDate("date").toLocalDate();
                s.setDate(date);
                String dayOfweek = date.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.JAPANESE);;
                s.setDayOfWeek(dayOfweek);
                LocalTime start_time = rs.getTime("arrival_time").toLocalTime();
                s.setStartTime(start_time);
                LocalTime end_time = rs.getTime("end_time").toLocalTime();
                s.setEndTime(end_time);
                int break_time = rs.getInt("break_time");
                s.setBreakTime(break_time);
                String status=rs.getString("status");
                s.setStatus(status);
                return s;
            }
        });
    }
}
