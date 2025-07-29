package com.kintaiTeam14.kintaiTeam14.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kintaiTeam14.kintaiTeam14.entity.Employee;
import com.kintaiTeam14.kintaiTeam14.repository.employee.EmployeeRepository;

import lombok.RequiredArgsConstructor;


/*データベースにデモ用のユーザ情報を初期設定するためのコードになります。
 * 実環境ではadminだけ残して消すようにしてください
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {
	   private final EmployeeRepository employeeRepository;
	    private final PasswordEncoder passwordEncoder;

	    @Override
	    @Transactional
	    public void run(ApplicationArguments args) throws Exception {
	        // adminユーザーが存在するかチェック
	        Employee adminUser = employeeRepository.findByEmployeeName("admin");
	        if (adminUser == null) {
	            // adminユーザーが存在しなければ作成
	            Employee employee = new Employee();
	            employee.setEmployeeId(111111L);
	            employee.setEmployeeName("admin");
	            employee.setPassword(passwordEncoder.encode("admin")); // 初期パスワードは適宜変更
	            employee.setDepartmentId("S001");   // 例：総務
	            employee.setIsPassword(true);       // 初期設定フラグ
	            employee.setPaidHoliday(30);         // 初期有休数
	            employee.setCompDay(10);             // 初期代休数
	            employee.setDepartmentHistory(null);

	            employeeRepository.insertEmployee(employee);
	            System.out.println("管理者(Admin)ユーザーを初期作成しました。");
	        }

	        Employee normalUser = employeeRepository.findByEmployeeName("user3");
	        if (normalUser == null) {
	            Employee employeeUser = new Employee();
	            employeeUser.setEmployeeId(100003L);
	            employeeUser.setEmployeeName("user3");
	            employeeUser.setPassword(passwordEncoder.encode("user3")); // 初期パスワードは適宜変更
	            employeeUser.setDepartmentId("D001");   // 例：開発
	            employeeUser.setIsPassword(true);
	            employeeUser.setPaidHoliday(20);
	            employeeUser.setCompDay(5);
	            employeeUser.setDepartmentHistory(null);
	            employeeRepository.insertEmployee(employeeUser);
	            System.out.println("一般(User)ユーザーを初期作成しました。");
	        }
	    }
	    
	    
	    
   }
