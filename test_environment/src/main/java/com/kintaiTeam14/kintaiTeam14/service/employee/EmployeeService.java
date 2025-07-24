package com.kintaiTeam14.kintaiTeam14.service.employee;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kintaiTeam14.kintaiTeam14.entity.Employee;
import com.kintaiTeam14.kintaiTeam14.repository.employee.EmployeeRepository;

import lombok.RequiredArgsConstructor;
/**
 * ユーザー登録やユーザー情報取得などのビジネスロジックを担当するサービスクラス。
 * 
 * - パスワードを安全にBCryptでハッシュ化してからDBに保存する。
 * - ユーザー名での検索をリポジトリに委譲する。
 */
@Service
@RequiredArgsConstructor

public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 新しいユーザーを登録する。
     * パスワードはBCryptでハッシュ化して保存することが必須。
     * 
     * @param username ユーザー名
     * @param rawPassword 平文パスワード
     */
    public void registerUser(String employeeName, String rawPassword, String departmentId) {
        // パスワードをハッシュ化
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Employeeエンティティにセット
        Employee employee = new Employee();
        // employeeIdはauto-incrementではない場合セットが必要ですが、ここでは省略
        employee.setEmployeeName(employeeName);
        employee.setPassword(encodedPassword);
        employee.setDepartmentId(departmentId);

        // 初期値をセット（要件に応じて修正）
        employee.setIsPassword(true);       // 初期設定フラグ（例：初回登録時はtrue）
        employee.setPaidHoliday(0);         // 有休数（例：0日で初期化）
        employee.setCompDay(0);             // 代休数（例：0日で初期化）
        employee.setDepartmentHistory(null); // 部署履歴（初期はnullや空文字など）

        // DBに保存
        employeeRepository.insertEmployee(employee);
    }

    /**
     * 指定されたユーザー名に対応するユーザー情報を取得する。
     * 
     * @param username ユーザー名
     * @return Userオブジェクト（存在しない場合はnull）
     */
    public Employee findByUsername(String username) {
        return employeeRepository.findByEmployeeName(username);
    }
    
    public List<Employee> getAllUsers() {
        return employeeRepository.findAll();
    }


    public void deleteUserById(Long id) {
        employeeRepository.deleteById(id);
    }

  
    public Optional<Employee> findUserById(Long id) {
        return employeeRepository.findById(id);
    }
    
    
    public void changePassword(long employeeId,String newPass) {
    	String encodednewPass= passwordEncoder.encode( newPass);
    		employeeRepository.updatePassword( employeeId,encodednewPass);
    }
    
    public boolean checkPassword(long employeeId,String currentPass) {
    	
    	
    	return employeeRepository.changePassword(employeeId, currentPass);
    	
    }
    public void changeIsPassword(long employeeId) {

    	
    	employeeRepository.changeIsPassword(employeeId);
    	
    }
    public int getPaidHoliday(long employeeId){
    	return employeeRepository.findById(employeeId).get().getPaidHoliday();
    }
    public int getCompday(long employeeId){
    	return employeeRepository.findById(employeeId).get().getCompDay();
    }
    
    
    






}