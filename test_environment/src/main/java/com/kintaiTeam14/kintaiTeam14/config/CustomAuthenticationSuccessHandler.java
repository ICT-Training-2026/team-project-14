package com.kintaiTeam14.kintaiTeam14.config;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.util.Collection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.kintaiTeam14.kintaiTeam14.service.performance.PerformanceService;

import lombok.AllArgsConstructor;

/**
 * Spring Securityの認証成功時に呼ばれるハンドラクラス。
 * 認証に成功したユーザーを特定し、任意のURLへリダイレクトさせる役割を持つ。
 * まだ機能作れていない管理者用ログイン画面と利用者用ログイン画面に分けるので修正
 */
@Component
@AllArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private final PerformanceService performanceService;
    /**
     * 認証成功時に呼ばれるメソッド。
     * @param request HTTPリクエスト情報
     * @param response HTTPレスポンス情報
     * @param authentication 認証情報（認証済みユーザの情報を含む）
     * @throws IOException リダイレクト処理で発生する可能性のある例外
     * @throws ServletException サーブレット処理例外
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 認証済みユーザーのユーザ名（principal名）を取得
        String employeename = authentication.getName();
        
   
       
        System.out.println("username"+employeename);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        System.out.println("authorities"+authorities);
        boolean isloginType = request.getParameter("loginType").equals("admin");
        String loginType = request.getParameter("loginType");
        System.out.println("loginType: " + loginType);

        boolean isAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        Long employeeId = null;
        boolean isPassword=true;
        
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            isPassword = ((CustomUserDetails) principal).getIsPassword();
        }
        
        
        if (principal instanceof CustomUserDetails) {
        	employeeId = ((CustomUserDetails) principal).getEmployeeId();
        }
        String redirectUrl;
        if(isPassword&&isAdmin) {
        	redirectUrl = "/" + employeeId+ "/passChange_form_user";
        }else if(isPassword&&!isAdmin){
        	
        	
        	
        	
        	
        	redirectUrl = "/" + employeeId+ "/passChange_form_user";
        }
        else if (isAdmin && isloginType) {
            // adminユーザは admin.html にリダイレクト
            redirectUrl = "/admin";
        } else if(!isAdmin && isloginType){
        	
        	 redirectUrl ="/login?role-error";
        	
        }else
        {
        	 LocalDate startDate = Year.now().atDay(1);
             LocalDate endDate = Year.now().atMonth(12).atEndOfMonth();

             boolean exists = performanceService.existsPerformancesForYear(employeeId, startDate, endDate);
             if (!exists) {
                 performanceService.createPerformancesForYear(employeeId, startDate, endDate);
             }
        	
        	
        	
        	
            // それ以外のユーザは username/top へリダイレクト
            redirectUrl = "/" + employeeId+ "/top";
        }

        // クライアントへリダイレクトを指示（HTTPステータス302）
        response.sendRedirect(redirectUrl);
    }
}