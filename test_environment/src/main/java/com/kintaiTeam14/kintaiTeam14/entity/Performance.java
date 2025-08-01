// Performance.java
package com.kintaiTeam14.kintaiTeam14.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class Performance {
    private Long id;                    // ID（DBのattend_idに対応する場合はLong型に変更推奨）
    private String dayOfWeek;          // 曜日
    private LocalDate date;            // 日付
    private LocalTime startTime;       // 開始時刻
    private LocalTime endTime;         // 終了時刻
    private int breakTime;             // 休憩時間 7/25修正。不具合修正のため。やっぱやめた
    private String status;             // ステータス
    private String reason;             // 理由
    private int atClassification;   //0～5

    /**
     * 2025年7月以外の日付のデータは無効とみなすメソッドを追加（例）
     */
    public boolean isInJuly2025() {
        return date != null && date.getYear() == 2025 && date.getMonthValue() == 7;
    }
}