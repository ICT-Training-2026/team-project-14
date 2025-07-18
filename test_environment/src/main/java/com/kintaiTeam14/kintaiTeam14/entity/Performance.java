package com.kintaiTeam14.kintaiTeam14.entity;
import java.time.DayOfWeek;
import java.time.LocalDate;

import lombok.Data;

@Data
public class Performance {
    private int id;                       // ID
    private String dayOfWeek;        // 曜日
    private LocalDate date;  //日付

}
