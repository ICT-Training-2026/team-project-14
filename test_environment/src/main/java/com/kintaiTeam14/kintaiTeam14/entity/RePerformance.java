// Performance.java
package com.kintaiTeam14.kintaiTeam14.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class RePerformance {
    private Long id;                    // ID（DBのattend_idに対応する場合はLong型に変更推奨）
    private LocalDate date;            // 日付
    private String dayOfWeek;          // 曜日
    private LocalTime startTime;       // 開始時刻
    private LocalTime endTime;         // 終了時刻
    private String status;             // ステータス
    private int breakTime;
    private String reason;             // 理由
    private int atClassification;   //0～5、出社区分
    private String correctReason;  //訂正理由
    private String diffReason; //差異理由


}