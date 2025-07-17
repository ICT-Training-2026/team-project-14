package com.generalfunction.demo.config;

import java.io.IOException;
import java.util.Collection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Spring Securityの認証成功時に呼ばれるハンドラクラス。
 * 認証に成功したユーザーを特定し、任意のURLへリダイレクトさせる役割を持つ。
 * まだ機能作れていない管理者用ログイン画面と利用者用ログイン画面に分けるので修正
 */
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

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
        String username = authentication.getName();
       
        System.out.println("username"+username);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        System.out.println("authorities"+authorities);
        boolean isloginType = request.getParameter("loginType").equals("admin");
        String loginType = request.getParameter("loginType");
        System.out.println("loginType: " + loginType);

        boolean isAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        Long userId = null;
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            userId = ((CustomUserDetails) principal).getUserId();
        }
        String redirectUrl;
        if (isAdmin && isloginType) {
            // adminユーザは admin.html にリダイレクト
            redirectUrl = "/admin";
        } else if(!isAdmin && isloginType){
        	
        	 redirectUrl ="/login?role-error";
        	
        }else
        {
            // それ以外のユーザは username/top へリダイレクト
            redirectUrl = "/" + userId+ "/top";
        }

        // クライアントへリダイレクトを指示（HTTPステータス302）
        response.sendRedirect(redirectUrl);
    }
}