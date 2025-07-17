package com.generalfunction.demo.service.login;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.generalfunction.demo.config.CustomUserDetails;
import com.generalfunction.demo.entity.Employee;
import com.generalfunction.demo.repository.login.EmployeeRepository;

import lombok.RequiredArgsConstructor;
/**
 * ログイン機能関連
 * Spring SecurityのUserDetailsServiceの実装クラス。
 * 
 * ユーザー名をもとにDBからユーザー情報を取得し、
 * 認証に必要なUserDetailsオブジェクトを生成して返す。
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	 private final EmployeeRepository employeeRepository;


	    @Override
	    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
	        // DBからユーザーを検索
	    	// DBからユーザーを検索
	        Employee employee = employeeRepository.findByEmployeeName(userName);

	        if (employee == null) {
	            throw new UsernameNotFoundException("ユーザーが見つかりません");
	        }

	        String roleName;
	        // department_idが'S001'ならadmin
	        if ("S001".equals(employee.getDepartmentId())) {
	            roleName = "ADMIN";
	        } else {
	            // それ以外は元のロール取得やデフォルトロール
	            // 例：roleRepositoryから取得する場合
	            // roleName = roleRepository.findRoleNameById(employee.getRoleId());
	            // あるいは一律USER
	            roleName = "USER";
	        }

	        return new CustomUserDetails(employee, roleName);
	    }
}