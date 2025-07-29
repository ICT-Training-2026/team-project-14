package com.kintaiTeam14.kintaiTeam14.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.kintaiTeam14.kintaiTeam14.entity.Employee;

/**
 * Spring SecurityのUserDetailsを実装したクラス。
 * DBのUserエンティティの情報をSpring Securityの認証情報に変換して提供する。
 */
public class CustomUserDetails implements UserDetails {

	 private final Employee employee;
	    private final String roleName; // 追加

	    public CustomUserDetails(Employee employee, String roleName) {
	        this.employee = employee;
	        this.roleName = roleName;
	    }
    /**
     * ユーザの権限を返す。DBのroleフィールドを"ROLE_"プレフィックス付きでセットする。
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + roleName));
    }
    
    public long getEmployeeId() {
    	return employee.getEmployeeId();
		
	}

    @Override
    public String getPassword() {
        return employee.getPassword();
    }

    @Override
    public String getUsername() {
        return employee.getEmployeeName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    
    



    public Employee getEmployee() {
        return employee;
    }
    
    public boolean getIsPassword() {
    	return employee.getIsPassword();
    	
    }
    
    @Override
    public boolean isEnabled() {
        Integer del = employee.getIsdelete();
        System.out.println(" isEnabled");
        System.out.println(employee);
        System.out.println(del);
        return (del == null || del == 0);
    }
    
    
    
    
    
    

}
