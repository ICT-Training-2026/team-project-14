package com.kintaiTeam14.kintaiTeam14.repository.holiday;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kintaiTeam14.kintaiTeam14.entity.Holiday;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Integer> {
    // 追加で必要なクエリがあればここに記述
}