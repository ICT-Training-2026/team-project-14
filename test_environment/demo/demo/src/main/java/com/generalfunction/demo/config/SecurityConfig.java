package com.generalfunction.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

/*
 * Spring Securityの設定クラス。
 * Webセキュリティ機能を有効化し、認証やアクセス制御のルールを定義する。
 */
@RequiredArgsConstructor
@Configuration // Springの設定クラスであることを示す
@EnableWebSecurity // Spring SecurityのWebセキュリティを有効にする
public class SecurityConfig {
	private final CustomAccessDeniedHandler accessDeniedHandler;
	
	/**
	 * パスワードをハッシュ化するためのPasswordEncoder Beanを登録。
	 * BCryptアルゴリズムを使い、安全にパスワードを管理する。
	 * @return BCryptPasswordEncoderのインスタンス
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * セキュリティフィルタチェーンのBeanを登録。
	 * HTTPリクエストの認可設定やログイン・ログアウトの挙動を定義する。
	 * 
	 * ・"/login", "/register", 静的リソース(css/js)は認証不要（permitAll）
	 * ・その他のリクエストは認証必須
	 * ・ログインページは"/login"に設定し、認証成功時はカスタムハンドラを利用
	 * ・ログアウトは全ユーザー許可
	 * 
	 * @param http HttpSecurityオブジェクト
	 * @return SecurityFilterChainインスタンス
	 * @throws Exception セキュリティ設定例外
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorizeRequests -> 
			authorizeRequests
			 	.requestMatchers("/admin/**").hasRole("ADMIN") 
				.requestMatchers("/login", "/css/**", "/js/**").permitAll() // 認証不要パス
				.anyRequest().authenticated() // それ以外は認証必須
		
				)
		.formLogin(form -> 
			form
				.loginPage("/login") // カスタムログインページのURLを指定
				.successHandler(new CustomAuthenticationSuccessHandler()) // 認証成功時の処理をカスタム
				.permitAll() // ログインページは認証不要
		)
		.logout(logout -> 
			logout. logoutUrl("/logout") // デフォルトは/logout
	        .logoutSuccessUrl("/login") // ログアウト後のリダイレクト先
	        .permitAll()
		) .exceptionHandling(exception -> 
        exception.accessDeniedHandler(accessDeniedHandler)
    );
		
		return http.build();
	}

}