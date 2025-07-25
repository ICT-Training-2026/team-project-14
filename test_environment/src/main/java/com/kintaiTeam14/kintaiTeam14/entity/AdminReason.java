package com.kintaiTeam14.kintaiTeam14.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reason")
public class AdminReason {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reason_id")
    private Integer reasonId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attend_id", referencedColumnName = "attend_id")
    private AdminAttendance attendance;

    @Column(name = "reason", length = 100)
    private String reason;

    // --- Getter/Setter ---

    public Integer getReasonId() {
        return reasonId;
    }

    public void setReasonId(Integer reasonId) {
        this.reasonId = reasonId;
    }

    public AdminAttendance getAttendance() {
        return attendance;
    }

    public void setAttendance(AdminAttendance attendance) {
        this.attendance = attendance;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}