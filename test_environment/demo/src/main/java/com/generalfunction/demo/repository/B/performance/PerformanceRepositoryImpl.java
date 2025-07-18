

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.generalfunction.demo.entity.Performance;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PerformanceRepositoryImpl implements PerformanceRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Performance> findAll() {
        String sql = "date FROM attendance";
        return jdbcTemplate.query(sql, new RowMapper<Performance>() {
            @Override
            public Performance mapRow(ResultSet rs, int rowNum) throws SQLException {
                Performance s = new Performance();
                LocalDate date = rs.getDate("date").toLocalDate();
                s.Date(date);
                return s;
            }
        });
    }






}
