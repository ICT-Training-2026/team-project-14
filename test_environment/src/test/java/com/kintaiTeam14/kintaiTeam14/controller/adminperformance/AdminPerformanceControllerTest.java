package com.kintaiTeam14.kintaiTeam14.controller.adminperformance;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.kintaiTeam14.kintaiTeam14.dto.AttendanceWithReasonDto;
import com.kintaiTeam14.kintaiTeam14.entity.AdminEmployee;
import com.kintaiTeam14.kintaiTeam14.service.adminperformance.AdminHolidayService;
import com.kintaiTeam14.kintaiTeam14.service.adminperformance.AdminPerformanceService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(AdminPerformanceController.class)
public class AdminPerformanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminPerformanceService attendanceService;

    @MockBean
    private AdminHolidayService holidayService;

    private int currentYear;
    private int currentMonth;

    @BeforeEach
    public void setup() {
        currentYear = LocalDate.now().getYear();
        currentMonth = LocalDate.now().getMonthValue();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testShowAchievement_NoParams_ShouldReturnDefaultYearMonthAndEmptyAttendance() throws Exception {
        when(holidayService.calculateScheduledWorkingHours(anyInt(), anyInt())).thenReturn(160);

        mockMvc.perform(get("/admin/achievement/performance"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/achievement"))
                .andExpect(model().attribute("yearList", org.hamcrest.Matchers.hasItem(currentYear)))
                .andExpect(model().attribute("monthList", org.hamcrest.Matchers.hasItem(currentMonth)))
                .andExpect(model().attribute("selectedYear", currentYear))
                .andExpect(model().attribute("selectedMonth", currentMonth))
                .andExpect(model().attribute("scheduledWorkingHours", 160.0))
                .andExpect(model().attribute("attendanceList", Collections.emptyList()))
                .andExpect(model().attribute("remainingAnnualLeave", 0))
                .andExpect(model().attribute("remainingSubstituteHoliday", 0))
                .andExpect(model().attribute("actualWorkingHours", 0.0))
                .andExpect(model().attribute("formattedOvertimeHours", "0.00"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testShowAchievement_WithValidParams_ShouldReturnAttendanceAndLeaves() throws Exception {
        int employeeId = 1;
        int year = currentYear;
        int month = currentMonth;

        when(holidayService.calculateScheduledWorkingHours(year, month)).thenReturn(160);

        AttendanceWithReasonDto attendanceDto = new AttendanceWithReasonDto(
                100L,
                employeeId,
                LocalDate.of(year, month, 1),
                LocalDateTime.of(year, month, 1, 9, 0),
                LocalDateTime.of(year, month, 1, 18, 0),
                1.0,
                0,
                "approved",
                "なし"
        );

        List<AttendanceWithReasonDto> attendanceList = Collections.singletonList(attendanceDto);
        when(attendanceService.getAttendanceWithReasonList(employeeId, year, month)).thenReturn(attendanceList);
        when(attendanceService.calculateActualWorkingHours(employeeId, year, month)).thenReturn(170.5);

        AdminEmployee adminEmployee = new AdminEmployee();
        adminEmployee.setPaidHoliday(10);
        adminEmployee.setCompDay(5);
        when(attendanceService.getEmployeeById(employeeId)).thenReturn(Optional.of(adminEmployee));

        mockMvc.perform(get("/admin/achievement/performance")
                .param("employeeId", String.valueOf(employeeId))
                .param("year", String.valueOf(year))
                .param("month", String.valueOf(month)))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/achievement"))
                .andExpect(model().attribute("selectedYear", year))
                .andExpect(model().attribute("selectedMonth", month))
                .andExpect(model().attribute("scheduledWorkingHours", 160.0))
                .andExpect(model().attributeExists("attendanceList"))
                .andExpect(model().attribute("remainingAnnualLeave", 10))
                .andExpect(model().attribute("remainingSubstituteHoliday", 5))
                .andExpect(model().attribute("actualWorkingHours", 170.5))
                .andExpect(model().attribute("formattedOvertimeHours", "10.50"))
                .andExpect(model().attribute("employeeId", employeeId));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testShowAchievement_WithEmployeeNotFound_ShouldReturnZeroLeaves() throws Exception {
        int employeeId = 2;
        int year = currentYear;
        int month = currentMonth;

        when(holidayService.calculateScheduledWorkingHours(year, month)).thenReturn(160);

        when(attendanceService.getAttendanceWithReasonList(employeeId, year, month)).thenReturn(Collections.emptyList());
        when(attendanceService.calculateActualWorkingHours(employeeId, year, month)).thenReturn(150.0);

        when(attendanceService.getEmployeeById(employeeId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/admin/achievement/performance")
                .param("employeeId", String.valueOf(employeeId))
                .param("year", String.valueOf(year))
                .param("month", String.valueOf(month)))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/achievement"))
                .andExpect(model().attribute("remainingAnnualLeave", 0))
                .andExpect(model().attribute("remainingSubstituteHoliday", 0))
                .andExpect(model().attribute("actualWorkingHours", 150.0))
                .andExpect(model().attribute("formattedOvertimeHours", "0.00"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testShowAchievement_OvertimeNegative_ShouldFormatZero() throws Exception {
        int employeeId = 3;
        int year = currentYear;
        int month = currentMonth;

        when(holidayService.calculateScheduledWorkingHours(year, month)).thenReturn(160);

        when(attendanceService.getAttendanceWithReasonList(employeeId, year, month)).thenReturn(Collections.emptyList());
        when(attendanceService.calculateActualWorkingHours(employeeId, year, month)).thenReturn(150.0);

        when(attendanceService.getEmployeeById(employeeId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/admin/achievement/performance")
                .param("employeeId", String.valueOf(employeeId))
                .param("year", String.valueOf(year))
                .param("month", String.valueOf(month)))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/achievement"))
                .andExpect(model().attribute("formattedOvertimeHours", "0.00"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testShowAchievement_AtClassificationMapping() throws Exception {
        int employeeId = 1;
        int year = currentYear;
        int month = currentMonth;

        when(holidayService.calculateScheduledWorkingHours(year, month)).thenReturn(160);

        List<AttendanceWithReasonDto> attendanceList = new ArrayList<>();
        int[] classifications = {0,1,2,3,4,5,6,7,8,99};
        for (int c : classifications) {
            attendanceList.add(new AttendanceWithReasonDto(
                100L + c,
                employeeId,
                LocalDate.of(year, month, 1),
                LocalDateTime.of(year, month, 1, 9, 0),
                LocalDateTime.of(year, month, 1, 18, 0),
                1.0,
                c,
                "approved",
                "reason" + c
            ));
        }

        when(attendanceService.getAttendanceWithReasonList(employeeId, year, month)).thenReturn(attendanceList);
        when(attendanceService.calculateActualWorkingHours(employeeId, year, month)).thenReturn(170.5);

        AdminEmployee adminEmployee = new AdminEmployee();
        adminEmployee.setPaidHoliday(10);
        adminEmployee.setCompDay(5);
        when(attendanceService.getEmployeeById(employeeId)).thenReturn(Optional.of(adminEmployee));

        MvcResult result = mockMvc.perform(get("/admin/achievement/performance")
                .param("employeeId", String.valueOf(employeeId))
                .param("year", String.valueOf(year))
                .param("month", String.valueOf(month)))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/achievement"))
                .andReturn();

        @SuppressWarnings("unchecked")
        List<Map<String,Object>> modelAttendanceList = (List<Map<String,Object>>) result.getModelAndView().getModel().get("attendanceList");

        Map<Integer,String> expectedMap = new HashMap<>();
        expectedMap.put(0, "出勤");
        expectedMap.put(1, "休日");
        expectedMap.put(2, "年休申請中");
        expectedMap.put(3, "振出申請中");
        expectedMap.put(4, "振休");
        expectedMap.put(5, "振出");
        expectedMap.put(6, "年休");
        expectedMap.put(7, "欠勤");
        expectedMap.put(8, "振休申請中");

        for (int i = 0; i < classifications.length; i++) {
            Map<String,Object> attendance = modelAttendanceList.get(i);
            String classificationStr = (String) attendance.get("classification");
            String expectedStr = expectedMap.getOrDefault(classifications[i], "未設定");
            assertEquals(expectedStr, classificationStr);
        }
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testShowAchievement_EmployeeIdNull_EmptyAttendance() throws Exception {
        when(holidayService.calculateScheduledWorkingHours(anyInt(), anyInt())).thenReturn(160);

        mockMvc.perform(get("/admin/achievement/performance")
                .param("employeeId", (String) null))
                .andExpect(status().isOk())
                .andExpect(model().attribute("attendanceList", Collections.emptyList()))
                .andExpect(model().attribute("remainingAnnualLeave", 0))
                .andExpect(model().attribute("remainingSubstituteHoliday", 0));
    }

}