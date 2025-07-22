//package com.kintaiTeam14.kintaiTeam14.controller.admin;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.kintaiTeam14.kintaiTeam14.service.employee.EmployeeService;
//
//import lombok.RequiredArgsConstructor;
//
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/admin/User-management")
//public class AdminUserManagement {
//	private final EmployeeService userService;
//	
//
//
//
//	 @GetMapping
//	    public String listUsers(Model model) {
//	        model.addAttribute("users", userService.getAllUsers());
//	        return "admin/user-management";
//	    }
//
//	    /**
//	     * ユーザー削除機能
//	     */
//	    @PostMapping("/delete/{id}")
//	    public String deleteUser(@PathVariable Long id) {
//	        userService.deleteUserById(id);
//	        return "redirect:/admin/User-management";
//	    }
//
//	    /**
//	     * admin権限付与機能
//	     */
////	    @PostMapping("/grant-admin/{id}")
////	    public String grantAdminRole(@PathVariable Long id) {
////	        userService.grantAdminRole(id);
////	        return "redirect:/admin/User-management";
////	    }
//	    
//	    /**
//	     * admin権限削除機能
//	     */
////	    @PostMapping("/revoke-admin/{id}")
////	    public String RevokeAdminRole(@PathVariable Long id) {
////	        userService.revokeAdminRole(id);
////	        return "redirect:/admin/User-management";
////	    }
////	    
////	    @GetMapping("/export")
////	    public void exportUsersCsv(HttpServletResponse response) throws IOException {
////	        response.setContentType("text/csv; charset=UTF-8");
////	        response.setHeader("Content-Disposition", "attachment; filename=\"users.csv\"");
////
////	        // OutputStreamを取得
////	        ServletOutputStream out = response.getOutputStream();
////	        // BOMを書き込む
////	        out.write(new byte[]{(byte)0xEF, (byte)0xBB, (byte)0xBF});
////
////	        // OutputStreamWriterでWriterを作成（UTF-8指定）
////	        try (Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
////	            // ヘッダー行
////	            writer.write("ID,ユーザー名,メール,役割\n");
////
////	            List<Employee> employees = userService.getAllUsers();
////
////	            for (Employee employee : employees) {
////	                String line = String.format("%d,%s,%s,%s\n",
////	                    employee.getId(),
////	                    escapeCsv(employee.getUsername()),
////	                    escapeCsv(employee.getEmail()),
////	                    escapeCsv(employee.getRole())
////	                );
////	                writer.write(line);
////	            }
////	            writer.flush();
////	        }
////	    }
//
//	    // CSVエスケープ処理例
////	    private String escapeCsv(String value) {
////	        if (value == null) return "";
////	        String escaped = value.replace("\"", "\"\"");
////	        if (escaped.contains(",") || escaped.contains("\"") || escaped.contains("\n")) {
////	            return "\"" + escaped + "\"";
////	        }
////	        return escaped;
////	    }
//
//	
//	}
//	
//	
//
//
