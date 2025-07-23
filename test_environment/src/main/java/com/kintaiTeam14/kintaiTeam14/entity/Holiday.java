package com.kintaiTeam14.kintaiTeam14.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "holiday")
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "holiday_id")
    private Integer holidayId;

    @Column(name = "holiday_date", nullable = false, unique = true)
    private LocalDate holidayDate;

    @Column(name = "holiday_name", length = 50)
    private String holidayName;

    @Column(name = "holiday_type", length = 20)
    private String holidayType;

    @Column(name = "holiday_note", length = 100)
    private String holidayNote;

    // getter and setter methods

    public Integer getHolidayId() {
        return holidayId;
    }

    public void setHolidayId(Integer holidayId) {
        this.holidayId = holidayId;
    }

    public LocalDate getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(LocalDate holidayDate) {
        this.holidayDate = holidayDate;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public String getHolidayType() {
        return holidayType;
    }

    public void setHolidayType(String holidayType) {
        this.holidayType = holidayType;
    }

    public String getHolidayNote() {
        return holidayNote;
    }

    public void setHolidayNote(String holidayNote) {
        this.holidayNote = holidayNote;
    }
}