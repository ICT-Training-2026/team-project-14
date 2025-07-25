package com.kintaiTeam14.kintaiTeam14.service.adminperformance;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.kintaiTeam14.kintaiTeam14.repository.adminperformance.AdminHolidayRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminHolidayService {

    private final AdminHolidayRepository holidayRepository;

    /**
     * 指定年月の土日祝日を除いた月間所定労働時間を計算する。
     * 1日あたり7時間労働で計算。
     *
     * @param year 年
     * @param month 月
     * @return 月間所定労働時間（時間）
     */
    public int calculateScheduledWorkingHours(int year, int month) {
        YearMonth ym = YearMonth.of(year, month);
        int daysInMonth = ym.lengthOfMonth();

        LocalDate startDate = ym.atDay(1);
        LocalDate endDate = ym.atEndOfMonth();

        // 土日のカウント
        int weekendCount = 0;
        for (int day = 1; day <= daysInMonth; day++) {
            DayOfWeek dow = ym.atDay(day).getDayOfWeek();
            if (dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY) {
                weekendCount++;
            }
        }

        // 祝日をDBから取得
        List<LocalDate> holidayDates = holidayRepository.findHolidayDatesBetween(startDate, endDate);

        // 祝日と土日の重複を除くためセット化
        Set<LocalDate> holidaySet = new HashSet<>(holidayDates);

        // 土日と重複しない祝日の数をカウント
        long holidayCountExcludingWeekends = holidaySet.stream()
            .filter(date -> {
                DayOfWeek dow = date.getDayOfWeek();
                return dow != DayOfWeek.SATURDAY && dow != DayOfWeek.SUNDAY;
            })
            .count();

        // 平日数 = 総日数 - 土日数 - 祝日(重複除く)
        int weekdays = daysInMonth - weekendCount - (int)holidayCountExcludingWeekends;

        // 1日7時間として計算
        return weekdays * 7;
    }
}