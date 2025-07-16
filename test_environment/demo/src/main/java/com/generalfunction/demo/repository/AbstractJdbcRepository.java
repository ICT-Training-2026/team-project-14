package com.generalfunction.demo.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public abstract class AbstractJdbcRepository<T, ID> implements GenericRepository<T, ID> {
    protected final JdbcTemplate jdbcTemplate;
    protected final RowMapper<T> rowMapper;
    

    
    
    

    @Override
    public T findById(String getTableName,String getIdColumnName,ID id) {
        String sql = "SELECT * FROM " + getTableName + " WHERE " + getIdColumnName + " = ?";
        List<T> list = jdbcTemplate.query(sql, rowMapper, id);
        return list.isEmpty() ? null : list.get(0);
    }
    
    @Override
    public List<T> findAll(String getTableName) {
        String sql = "SELECT * FROM " + getTableName;
        return jdbcTemplate.query(sql, rowMapper);
    }

	 @Override
	    public int deleteById(String getTableName,String getIdColumnName,ID id) {
	        String sql = "DELETE FROM " + getTableName + " WHERE " + getIdColumnName+ " = ?";
	        return jdbcTemplate.update(sql, id);
	    }

}
