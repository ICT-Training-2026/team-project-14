package com.kintaiTeam14.kintaiTeam14.controller.admin;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kintaiTeam14.kintaiTeam14.entity.Holiday;
import com.kintaiTeam14.kintaiTeam14.service.holiday.HolidayService;

@RestController
@RequestMapping("/api/holidays")
public class HolidayController {

	private final HolidayService holidayService;

	public HolidayController(HolidayService holidayService) {
		this.holidayService = holidayService;
	}

	// 一覧取得
	@GetMapping
	public List<Holiday> getAllHolidays() {
		return holidayService.findAll();
	}

	// 1件取得
	@GetMapping("/{id}")
	public ResponseEntity<Holiday> getHolidayById(@PathVariable Integer id) {
		Optional<Holiday> holiday = holidayService.findById(id);
		return holiday.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	// 新規登録（ここがPOST）
	@PostMapping
	public ResponseEntity<Holiday> createHoliday(@RequestBody Holiday holiday) {
		Holiday savedHoliday = holidayService.save(holiday);
		return ResponseEntity.ok(savedHoliday);
	}

	// 更新
	@PutMapping("/{id}")
	public ResponseEntity<Holiday> updateHoliday(@PathVariable Integer id, @RequestBody Holiday updatedHoliday) {
		Optional<Holiday> optionalHoliday = holidayService.findById(id);
		if (!optionalHoliday.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Holiday holiday = optionalHoliday.get();
		holiday.setHolidayDate(updatedHoliday.getHolidayDate());
		holiday.setHolidayName(updatedHoliday.getHolidayName());
		holiday.setHolidayType(updatedHoliday.getHolidayType());
		holiday.setHolidayNote(updatedHoliday.getHolidayNote());
		Holiday savedHoliday = holidayService.save(holiday);
		return ResponseEntity.ok(savedHoliday);
	}

	// 削除
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteHoliday(@PathVariable Integer id) {
		if (!holidayService.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		holidayService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/export")
    public void exportCsv(HttpServletResponse response) throws Exception {
        String fileName = URLEncoder.encode("holidays.csv", StandardCharsets.UTF_8);
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName);

        List<Holiday> holidays = holidayService.findAll();

        try (PrintWriter writer = response.getWriter()) {
            writer.println("日付,名称,種別,備考");
            for (Holiday h : holidays) {
                writer.printf("%s,%s,%s,%s%n",
                        h.getHolidayDate() != null ? h.getHolidayDate() : "",
                        escapeCsv(h.getHolidayName()),
                        escapeCsv(h.getHolidayType()),
                        escapeCsv(h.getHolidayNote()));
            }
        }
    }

	private String escapeCsv(String s) {
		if (s == null)
			return "";
		if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
			s = s.replace("\"", "\"\"");
			return "\"" + s + "\"";
		}
		return s;
	}
}