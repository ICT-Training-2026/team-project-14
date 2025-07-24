package com.kintaiTeam14.kintaiTeam14.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private final CustomAccessDeniedHandler accessDeniedHandler;
	private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

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
				.successHandler(customAuthenticationSuccessHandler) // 認証成功時の処理をカスタム
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