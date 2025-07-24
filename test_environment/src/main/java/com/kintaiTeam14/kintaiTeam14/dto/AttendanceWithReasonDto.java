package com.kintaiTeam14.kintaiTeam14.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttendanceWithReasonDto {
    private Long attendId;
    private Integer employeeId;
    private LocalDate date;
    private LocalDateTime arrivalTime;
    private LocalDateTime endTime;
    private Integer breakTime;
    private Integer atClassification;
    private String status;
    private String reason;

    // コンストラクタ
    public AttendanceWithReasonDto(Long attendId, Integer employeeId, LocalDate date,
                                  LocalDateTime arrivalTime, LocalDateTime endTime,
                                  Integer breakTime, Integer atClassification,
                                  String status, String reason) {
        this.attendId = attendId;
        this.employeeId = employeeId;
        this.date = date;
        this.arrivalTime = arrivalTime;
        this.endTime = endTime;
        this.breakTime = breakTime;
        this.atClassification = atClassification;
        this.status = status;
        this.reason = reason;
    }

    // Getter/Setter
    public Long getAttendId() {
        return attendId;
    }

    public void setAttendId(Long attendId) {
        this.attendId = attendId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}