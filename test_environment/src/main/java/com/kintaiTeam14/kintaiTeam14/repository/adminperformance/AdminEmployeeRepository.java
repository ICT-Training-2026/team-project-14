package com.kintaiTeam14.kintaiTeam14.repository.adminperformance;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kintaiTeam14.kintaiTeam14.entity.AdminEmployee;

@Repository
public interface AdminEmployeeRepository extends JpaRepository<AdminEmployee, Integer> {

    Optional<AdminEmployee> findByEmployeeId(Integer employeeId);

}