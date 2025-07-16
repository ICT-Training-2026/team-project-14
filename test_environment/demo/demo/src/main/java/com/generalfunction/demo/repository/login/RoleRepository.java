package com.generalfunction.demo.repository.login;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RoleRepository {
    private final JdbcTemplate jdbcTemplate;

    public Integer findRoleIdByRoleName(String roleName) {
        String sql = "SELECT role_id FROM role WHERE role_name = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, roleName);
    }
    
    public String findRoleNameById(Integer roleId) {
        String sql = "SELECT role_name FROM role WHERE role_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, roleId);
    }
}