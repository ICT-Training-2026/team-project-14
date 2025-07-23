package com.kintaiTeam14.kintaiTeam14.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    private Long attendId;             // attend_id (主キー, auto_increment)

    private Integer employeeId;        // employee_id (外部キー想定)

    private LocalDateTime arrivalTime; // arrival_time (出勤時刻)

    private LocalDateTime endTime;     // end_time (退勤時刻)

    private Byte breakTime;            // break_time (休憩時間)

    private Boolean atClassification;  // at_classification (出勤区分)
    // tinyint(1)はBooleanでマッピングするのが一般的です

    private Byte overtime;             // overtime (残業時間)

    private String status;             // status (varchar(5))

    private LocalDateTime createdAt;   // created_at

    private LocalDateTime updatedAt;   // updated_at

    private LocalDate date;            // date (日付のみ)
}
