package com.kintaiTeam14.kintaiTeam14.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reason")
public class AdminReason {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reason_id")
    private Integer reasonId;

    @Column(name = "attend_id", nullable = false)
    private Long attendId;

    @Column(name = "reason", length = 100)
    private String reason;

    // --- Getter/Setter ---

    public Integer getReasonId() {
        return reasonId;
    }

    public void setReasonId(Integer reasonId) {
        this.reasonId = reasonId;
    }

    public Long getAttendId() {
        return attendId;
    }

    public void setAttendId(Long attendId) {
        this.attendId = attendId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}