package com.kintaiTeam14.kintaiTeam14.controller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kintaiTeam14.kintaiTeam14.form.UserEditForm;
import com.kintaiTeam14.kintaiTeam14.form.UserRegistForm;
import com.kintaiTeam14.kintaiTeam14.form.UserSearchForm;
import com.kintaiTeam14.kintaiTeam14.service.employee.EmployeeService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class AdminUserManagement {
	private final EmployeeService userService;
	
	//ユーザー検索用処理
	@PostMapping("/admin/user-list")
	public String searchUser(@ModelAttribute UserSearchForm f, Model m) {
		List<Map<String, Object>> users = userService.findByUsernameAll(f.getSearchWord());
		System.out.println("search-query："+f.getSearchWord());
		System.out.println(users);
		m.addAttribute("users",users);
		return "admin/user-management";
	}
	
	//ユーザー新規登録処理
	@PostMapping("/admin/user-regist")
	public String userRegist(@ModelAttribute UserRegistForm f) {
		return "/admin/user-regist";
	}
	
	//ユーザー新規登録処理(リダイレクト用)
	@GetMapping("/admin/user-regist")
	public String userRegistRedirect(@ModelAttribute UserRegistForm f) {
		return "/admin/user-regist";
	}
	
	//ユーザー情報編集処理
	@PostMapping("admin/edit/{empId}")
	public String editUser(@ModelAttribute UserEditForm f,@PathVariable Long empId,Model model) {
		model.addAttribute("form", f);
		return "/admin/user-edit";
	}

	/**
	 * ユーザー削除機能
	 */
	@PostMapping("admin/delete/{empId}")
	public String deleteUser(@PathVariable Long empId,RedirectAttributes ra) {
		
		userService.deleteUserById(empId);
		ra.addFlashAttribute("msg", "削除しました");
		return "redirect:/admin/User-management";
	}
	
//	@GetMapping
//	public String listUsers(Model model) {
//		model.addAttribute("users", userService.getAllUsers());
//		return "admin/user-management";
//	}

	/**
	 * admin権限付与機能
	 */
	//	    @PostMapping("/grant-admin/{id}")
	//	    public String grantAdminRole(@PathVariable Long id) {
	//	        userService.grantAdminRole(id);
	//	        return "redirect:/admin/User-management";
	//	    }

	/**
	 * admin権限削除機能
	 */
	//	    @PostMapping("/revoke-admin/{id}")
	//	    public String RevokeAdminRole(@PathVariable Long id) {
	//	        userService.revokeAdminRole(id);
	//	        return "redirect:/admin/User-management";
	//	    }
	//	    
	//	    @GetMapping("/export")
	//	    public void exportUsersCsv(HttpServletResponse response) throws IOException {
	//	        response.setContentType("text/csv; charset=UTF-8");
	//	        response.setHeader("Content-Disposition", "attachment; filename=\"users.csv\"");
	//
	//	        // OutputStreamを取得
	//	        ServletOutputStream out = response.getOutputStream();
	//	        // BOMを書き込む
	//	        out.write(new byte[]{(byte)0xEF, (byte)0xBB, (byte)0xBF});
	//
	//	        // OutputStreamWriterでWriterを作成（UTF-8指定）
	//	        try (Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
	//	            // ヘッダー行
	//	            writer.write("ID,ユーザー名,メール,役割\n");
	//
	//	            List<Employee> employees = userService.getAllUsers();
	//
	//	            for (Employee employee : employees) {
	//	                String line = String.format("%d,%s,%s,%s\n",
	//	                    employee.getId(),
	//	                    escapeCsv(employee.getUsername()),
	//	                    escapeCsv(employee.getEmail()),
	//	                    escapeCsv(employee.getRole())
	//	                );
	//	                writer.write(line);
	//	            }
	//	            writer.flush();
	//	        }
	//	    }

	// CSVエスケープ処理例
	//	    private String escapeCsv(String value) {
	//	        if (value == null) return "";
	//	        String escaped = value.replace("\"", "\"\"");
	//	        if (escaped.contains(",") || escaped.contains("\"") || escaped.contains("\n")) {
	//	            return "\"" + escaped + "\"";
	//	        }
	//	        return escaped;
	//	    }


}




