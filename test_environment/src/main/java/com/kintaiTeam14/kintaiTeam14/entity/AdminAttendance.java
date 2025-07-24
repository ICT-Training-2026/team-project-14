package com.kintaiTeam14.kintaiTeam14.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance")
public class AdminAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attend_id")
    private Long attendId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private AdminEmployee employee;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "break_time", nullable = false)
    private Integer breakTime = 1; // デフォルト1

    @Column(name = "at_classification", nullable = false)
    private Integer atClassification;

    @Column(name = "overtime")
    private Integer overtime;

    @Column(name = "status", length = 5, nullable = false)
    private String status;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // AdminReasonとの関連を所有側にして、insertable/updatableはtrueに設定
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "attend_id", referencedColumnName = "attend_id", insertable = true, updatable = true)
    private AdminReason reason;

    // --- Getter/Setter ---

    public Long getAttendId() {
        return attendId;
    }

    public void setAttendId(Long attendId) {
        this.attendId = attendId;
    }

    public AdminEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(AdminEmployee employee) {
        this.employee = employee;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(Integer breakTime) {
        this.breakTime = breakTime;
    }

    public Integer getAtClassification() {
        return atClassification;
    }

    public void setAtClassification(Integer atClassification) {
        this.atClassification = atClassification;
    }

    public Integer getOvertime() {
        return overtime;
    }

    public void setOvertime(Integer overtime) {
        this.overtime = overtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public AdminReason getReason() {
        return reason;
    }

    public void setReason(AdminReason reason) {
        this.reason = reason;
    }
}