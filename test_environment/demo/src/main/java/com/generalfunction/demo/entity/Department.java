package com.generalfunction.demo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("department")
public class Department {
    @Id
    @Column("department_id")
    private Integer departmentId;      // 部署ID（主キー）

    @Column("department_name")
    private String departmentName;     // 部署名
}