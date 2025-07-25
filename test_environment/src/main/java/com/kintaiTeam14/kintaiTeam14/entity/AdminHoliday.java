package com.kintaiTeam14.kintaiTeam14.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "holiday")
@Data
public class AdminHoliday {

    @Id
    @Column(name = "holiday_id")
    private Integer holidayId;

    @Column(name = "holiday_date")
    private LocalDate holidayDate;

    @Column(name = "holiday_name")
    private String holidayName;

    @Column(name = "holiday_type")
    private String holidayType;

    @Column(name = "holiday_note")
    private String holidayNote;

}