package com.kintaiTeam14.kintaiTeam14.controller.user;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.kintaiTeam14.kintaiTeam14.form.ChangePasswordForm;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kintaiTeam14.kintaiTeam14.entity.Employee;
import com.kintaiTeam14.kintaiTeam14.service.attendance.AttendanceService;
import com.kintaiTeam14.kintaiTeam14.service.employee.EmployeeService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class shinseiController {
	 private final EmployeeService employeeService;
	 private final AttendanceService attendanceService;
	@PostMapping("/{employeeId}/top/shinsei_user")
	public String kakusyusinsei(Model m,@PathVariable Long employeeId) {
		m.addAttribute("employeeId", employeeId);

		return "user/shinsei";
	}
	@GetMapping("/{employeeId}/top/shinsei_user")
	public String kakusyusinseiget(Model m,@PathVariable Long employeeId) {
		m.addAttribute("employeeId", employeeId);

		return "user/shinsei";
	}
	
	
	
	@PostMapping("/{employeeId}/top/shinsei_user/nenkyu")
	public String nenkyu(Model m,@PathVariable Long employeeId) {
		m.addAttribute("employeeId", employeeId);
		 Optional<Employee> employeeOpt = employeeService.findUserById(employeeId);

		    // 中身を取り出してセット
		    Employee employee = employeeOpt.orElse(null);
		m.addAttribute("employee",employee);
		return "user/nenkyu";
	}
	
	   @PostMapping("/{employeeId}/top/shinsei_user/nenkyu/apply")
	   @ResponseBody
	    public Map<String, Object> applyNenkyu(Model m,@PathVariable Long employeeId,@RequestBody List<Map<String, Object>> requestList) {
	        // ここでrequestListの内容をDB保存等のロジックに利用
		   boolean hasError = false;
		    String errorMsg = "";
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
		    Map<String, Object> result = new HashMap<>();

		    Set<String> dateSet = new HashSet<>();
		    LocalDate today = LocalDate.now();
		    // 仮：申請済み日付リスト
		    List<String> appliedDates = List.of("2024/07/10", "2024/07/11");

		    for (Map<String, Object> row : requestList) {
		    	String dateStr = (String) row.get("date");
		        LocalDate targetDate = LocalDate.parse(dateStr.replace("/", "-"), formatter);
		        // 重複チェック
		        if (!dateSet.add(dateStr)) {
		            hasError = true;
		            errorMsg = "日付「" + dateStr + "」が重複しています。";
		            break;
		        }
		        // 過去日付チェック
		       
		        if (targetDate.isBefore(today)) {
		            hasError = true;
		            errorMsg = "日付「" + dateStr + "」は過去日付です。";
		            break;
		        }
		        // 申請済みチェック
		        if (appliedDates.contains(dateStr)) {
		            hasError = true;
		            errorMsg = "日付「" + dateStr + "」はすでに申請済みです。";
		            break;
		        }
		    }
		    
		    
		    
		    
		    
		    for (Map<String, Object> row : requestList) {
		        String dateStr = (String) row.get("date");
		        LocalDate targetDate1 = LocalDate.parse(dateStr.replace("/", "-"), formatter);

		        // ここでDB更新
		        int updated = attendanceService.updateAtClassificationService(employeeId, targetDate1, (byte)2);

		        // （オプション）更新件数が0ならエラーなどの判定もできる
		        if (updated == 0) {
		            hasError = true;
		            errorMsg = "該当データがありません（" + dateStr + "）";
		            break;
		        }
		    }

		    if (hasError) {
		        result.put("success", false);
		        result.put("errorMsg", errorMsg);
		        return result;
		    }
		    
		    
		    
		    
		    
		    // 正常処理...
		    result.put("success", true);
		    
		    
		    
		    
		    return result;
	    }
	   
	
	
	
	
	@PostMapping("/{employeeId}/top/shinsei_user/hurikyu")
	public String hurikyu(Model m,@PathVariable Long employeeId) {
		m.addAttribute("employeeId", employeeId);
		return "user/hurikyu";
	}
}
