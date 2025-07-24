package com.kintaiTeam14.kintaiTeam14.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee")
public class AdminEmployee {

    @Id
    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "department_id", length = 4, nullable = false)
    private String departmentId;

    @Column(name = "employee_name", length = 30, nullable = false)
    private String employeeName;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "is_password", nullable = false)
    private Boolean isPassword;

    @Column(name = "paid_holiday", nullable = false)
    private Integer paidHoliday;

    @Column(name = "comp_day", nullable = false)
    private Integer compDay;

    @Column(name = "department_history", length = 30)
    private String departmentHistory;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // --- Getter/Setter ---

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsPassword() {
        return isPassword;
    }

    public void setIsPassword(Boolean isPassword) {
        this.isPassword = isPassword;
    }

    public Integer getPaidHoliday() {
        return paidHoliday;
    }

    public void setPaidHoliday(Integer paidHoliday) {
        this.paidHoliday = paidHoliday;
    }

    public Integer getCompDay() {
        return compDay;
    }

    public void setCompDay(Integer compDay) {
        this.compDay = compDay;
    }

    public String getDepartmentHistory() {
        return departmentHistory;
    }

    public void setDepartmentHistory(String departmentHistory) {
        this.departmentHistory = departmentHistory;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}