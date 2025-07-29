package com.kintaiTeam14.kintaiTeam14.controller.user;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kintaiTeam14.kintaiTeam14.entity.Employee;
import com.kintaiTeam14.kintaiTeam14.service.attendance.AttendanceSyouninService;
import com.kintaiTeam14.kintaiTeam14.service.employee.EmployeeService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class shinseiController {
	 private final EmployeeService employeeService;
	 private final AttendanceSyouninService attendanceService;
	@PostMapping("/{employeeId}/top/shinsei")
	public String kakusyusinsei(Model m,@PathVariable Long employeeId) {
		m.addAttribute("employeeId", employeeId);
	  

		return "redirect:/" + employeeId + "/top/shinsei";
	}
	@GetMapping("/{employeeId}/top/shinsei")
	@PreAuthorize("#employeeId== principal.employeeId")
	public String kakusyusinseiget(Model m,@PathVariable Long employeeId) {
		m.addAttribute("employeeId", employeeId);

		return "user/shinsei";
	}
	
	
	
	@PostMapping("/{employeeId}/top/shinsei/nenkyu")
	public String nenkyu(Model m,@PathVariable Long employeeId) {
		m.addAttribute("employeeId", employeeId);
		  List<String> appliedDates = attendanceService.findDatesByEmployeeIdAndAtClassificationService(employeeId, 2);
		  List<String> appliedDates4 = attendanceService.findDatesByEmployeeIdAndAtClassificationService(employeeId, 4);
		    int kakusyusinseiPaidHoliday=employeeService.getPaidHoliday(employeeId);
		    int  kakusyusinseiAppliedDatesSize =  appliedDates.size();
		    int  kakusyusinseiAppliedDatesSize4 =  appliedDates4.size();
		    System.out.println(kakusyusinseiPaidHoliday- kakusyusinseiAppliedDatesSize);
		    m.addAttribute("PaidHoliday", kakusyusinseiPaidHoliday- kakusyusinseiAppliedDatesSize-kakusyusinseiAppliedDatesSize4);
		    
		    System.out.println(appliedDates);
		 Optional<Employee> employeeOpt = employeeService.findUserById(employeeId);

		    // 中身を取り出してセット
		    Employee employee = employeeOpt.orElse(null);
		m.addAttribute("employee",employee);
		return "redirect:/{employeeId}/top/shinsei/nenkyu";
	}
	
	@GetMapping("/{employeeId}/top/shinsei/nenkyu")
	@PreAuthorize("#employeeId== principal.employeeId")
	public String nenkyuGet(Model m, @PathVariable Long employeeId) {
	    // 申請済み日付リストを取得
	    List<String> appliedDates = attendanceService.findDatesByEmployeeIdAndAtClassificationService(employeeId, 2);
	    List<String> appliedDates4 = attendanceService.findDatesByEmployeeIdAndAtClassificationService(employeeId, 4);
	    // 年休残日数を計算
	    int paidHoliday = employeeService.getPaidHoliday(employeeId);
	    int appliedDatesSize = appliedDates.size();
	    int appliedDatesSize4 = appliedDates4.size();
	    int remainingPaidHoliday = paidHoliday - appliedDatesSize-appliedDatesSize4;

	    // 必要なデータをModelにセット
	    m.addAttribute("PaidHoliday", remainingPaidHoliday);
	    m.addAttribute("appliedDates", appliedDates); // もし画面で使うなら

	    // 社員情報も必要なら
	    Optional<Employee> employeeOpt = employeeService.findUserById(employeeId);
	    Employee employee = employeeOpt.orElse(null);
	    m.addAttribute("employee", employee);

	    m.addAttribute("employeeId", employeeId);

	    return "user/nenkyu";
	}
	
	
	
	
	
	
	
	   @PostMapping("/{employeeId}/top/shinsei/nenkyu/apply")
	   @ResponseBody
	    public Map<String, Object> applyNenkyu(Model m,@PathVariable Long employeeId,@RequestBody List<Map<String, Object>> requestList) {
	        // ここでrequestListの内容をDB保存等のロジックに利用
		   System.out.println("/{employeeId}/top/shinsei/nenkyu/apply");
		   boolean hasError = false;
		    String errorMsg = "";
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
		    Map<String, Object> result = new HashMap<>();

		    Set<String> dateSet = new HashSet<>();
		    LocalDate today = LocalDate.now();
		    // 仮：申請済み日付リスト
		    
		    List<String> appliedDates = attendanceService.findDatesByEmployeeIdAndAtClassificationService(employeeId, 2);
		    List<String> appliedDates4 = attendanceService.findDatesByEmployeeIdAndAtClassificationService(employeeId, 4);
		    int kakusyusinseiPaidHoliday=employeeService.getPaidHoliday(employeeId);
		    int  kakusyusinseiAppliedDatesSize =  appliedDates.size();
		    int  kakusyusinseiAppliedDatesSize4 =  appliedDates4.size();
		    System.out.println(kakusyusinseiPaidHoliday- kakusyusinseiAppliedDatesSize);
		    m.addAttribute("PaidHoliday", kakusyusinseiPaidHoliday- kakusyusinseiAppliedDatesSize- kakusyusinseiAppliedDatesSize4);
		    
		    for (Map<String, Object> row : requestList) {
		    	String dateStr = (String) row.get("date");
		        LocalDate targetDate = LocalDate.parse(dateStr.replace("/", "-"), formatter);
		        // 重複チェック
		        if (!dateSet.add(dateStr)) {
		            hasError = true;
		           
		            errorMsg = "日付「" + dateStr + "」が重複しています。";
		            System.out.println( errorMsg);
		            break;
		        }
		        // 過去日付チェック
		       
		        if (targetDate.isBefore(today)) {
		            hasError = true;
		            errorMsg = "日付「" + dateStr + "」は過去日付です。";
		            System.out.println( errorMsg);
		            break;
		        }
		        // 申請済みチェック
		        if (appliedDates.contains(dateStr)) {
		            hasError = true;
		            errorMsg = "日付「" + dateStr + "」はすでに申請済みです。";
		            System.out.println( errorMsg);
		            break;
		        }
		    }
		    
		    
		    
		    
		    if(!hasError) {
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
		    }

		    if (hasError) {
		        result.put("success", false);
		        result.put("errorMsg", errorMsg);
		        System.out.println("送信ミス");
		        
		        return result;
		    }
		    
		    
		    
		    
		    
		    // 正常処理...
		    result.put("success", true);
		    
		    
		    
		    
		    return result;
	    }
	   
	
	
	
	
	@PostMapping("/{employeeId}/top/shinsei/hurikyu")
	public String hurikyu(Model m,@PathVariable Long employeeId) {
		m.addAttribute("employeeId", employeeId);
		  List<String> appliedDates = attendanceService.findDatesByEmployeeIdAndAtClassificationService(employeeId, 3);
		  List<String> appliedDates5 = attendanceService.findDatesByEmployeeIdAndAtClassificationService(employeeId, 5);
		    int kakusyusinseiPaidHoliday=employeeService.getCompday(employeeId);
		    int  kakusyusinseiAppliedDatesSize =  appliedDates.size();
		    int  kakusyusinseiAppliedDatesSize5 =  appliedDates5.size();
		    System.out.println(kakusyusinseiPaidHoliday- kakusyusinseiAppliedDatesSize);
		    m.addAttribute("Compday", kakusyusinseiPaidHoliday- kakusyusinseiAppliedDatesSize-kakusyusinseiAppliedDatesSize5);
		    
		    System.out.println(appliedDates);
		 Optional<Employee> employeeOpt = employeeService.findUserById(employeeId);

		    // 中身を取り出してセット
		    Employee employee = employeeOpt.orElse(null);
		m.addAttribute("employee",employee);
		return "redirect:/{employeeId}/top/shinsei/hurikyu";
	}
	
	
	@GetMapping("/{employeeId}/top/shinsei/hurikyu")
	@PreAuthorize("#employeeId== principal.employeeId")
	public String hurikyuGet(Model m, @PathVariable Long employeeId) {
	    // 申請済み日付リストを取得
	    List<String> appliedDates = attendanceService.findDatesByEmployeeIdAndAtClassificationService(employeeId, 3);
	    List<String> appliedDates5 = attendanceService.findDatesByEmployeeIdAndAtClassificationService(employeeId, 5);

	    // 年休残日数を計算
	    int paidHoliday = employeeService.getCompday(employeeId);
	    
	    
	    int appliedDatesSize = appliedDates.size();
	    int appliedDatesSize5 = appliedDates5.size();
	    int remainingPaidHoliday = paidHoliday - appliedDatesSize-appliedDatesSize5;

	    // 必要なデータをModelにセット
	    m.addAttribute("Compday", remainingPaidHoliday);
	    m.addAttribute("appliedDates", appliedDates); // もし画面で使うなら

	    // 社員情報も必要なら
	    Optional<Employee> employeeOpt = employeeService.findUserById(employeeId);
	    Employee employee = employeeOpt.orElse(null);
	    m.addAttribute("employee", employee);

	    m.addAttribute("employeeId", employeeId);

	    return "user/hurikyu";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	   @PostMapping("/{employeeId}/top/shinsei/hurikyu/apply")
	   @ResponseBody
	    public Map<String, Object> applyHurikyu(Model m,@PathVariable Long employeeId,@RequestBody List<Map<String, Object>> requestList) {
	        // ここでrequestListの内容をDB保存等のロジックに利用
		   boolean hasError = false;
		    String errorMsg = "";
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
		    Map<String, Object> result = new HashMap<>();

		    Set<String> dateSet = new HashSet<>();
		    LocalDate today = LocalDate.now();
		    // 仮：申請済み日付リスト
		    
		    List<String> appliedDates = attendanceService.findDatesByEmployeeIdAndAtClassificationService(employeeId, 3);
		    List<String> appliedDates5 = attendanceService.findDatesByEmployeeIdAndAtClassificationService(employeeId, 5);
		    int hurikyusinseiCompday=employeeService.getCompday(employeeId);
		    int  hurikyusinseiAppliedDatesSize =  appliedDates.size();
		    int  hurikyusinseiAppliedDatesSize5 =  appliedDates5.size();
		    System.out.println(hurikyusinseiCompday -hurikyusinseiAppliedDatesSize);
		    m.addAttribute("PaidHoliday", hurikyusinseiCompday- hurikyusinseiAppliedDatesSize-hurikyusinseiAppliedDatesSize5);
		    
		    for (Map<String, Object> row : requestList) {
		    	String dateStr = (String) row.get("date");
		        LocalDate targetDate = LocalDate.parse(dateStr.replace("/", "-"), formatter);
		        // 重複チェック
		        if (!dateSet.add(dateStr)) {
		            hasError = true;
		            errorMsg = "日付「" + dateStr + "」が重複しています。";
		            System.out.println( errorMsg);
		            break;
		        }
		        // 過去日付チェック
		       
		        if (targetDate.isBefore(today)) {
		            hasError = true;
		            errorMsg = "日付「" + dateStr + "」は過去日付です。";
		            System.out.println( errorMsg);
		            break;
		        }
		        // 申請済みチェック
		        if (appliedDates.contains(dateStr)) {
		            hasError = true;
		            errorMsg = "日付「" + dateStr + "」はすでに申請済みです。";
		            System.out.println( errorMsg);
		            break;
		        }
		    }
		    
		    
		    
		    
		    if(!hasError) {
			    for (Map<String, Object> row : requestList) {
			        String dateStr = (String) row.get("date");
			        LocalDate targetDate1 = LocalDate.parse(dateStr.replace("/", "-"), formatter);
	
			        // ここでDB更新
			        int updated = attendanceService.updateAtClassificationService(employeeId, targetDate1, (byte)3);
	
			        // （オプション）更新件数が0ならエラーなどの判定もできる
			        if (updated == 0) {
			            hasError = true;
			            errorMsg = "該当データがありません（" + dateStr + "）";
			            break;
			        }
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
	
	
	
	
	
	
	
	
	
	
	
	
}
