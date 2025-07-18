package com.kintaiTeam14.kintaiTeam14.repository.performance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
        String sql = "SELECT date FROM attendance";
        return jdbcTemplate.query(sql, new RowMapper<Performance>() {
            @Override
            public Performance mapRow(ResultSet rs, int rowNum) throws SQLException {
                Performance s = new Performance();
                LocalDate date = rs.getDate("date").toLocalDate();
                s.setDate(date);
                //dateを曜日に変換する関数を作成してほしい、dayofweek
                  String dayOfweek = date.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.JAPANESE);;
                s.setDayOfWeek(dayOfweek);
                return s;
            }
        });
    }






}
