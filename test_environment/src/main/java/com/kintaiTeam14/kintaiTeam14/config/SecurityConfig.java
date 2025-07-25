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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                // 勤怠CSV出力エンドポイントをpermitAll
                .requestMatchers("/admin/export-attendance").permitAll()
                // 祝日CSV出力エンドポイントもpermitAll
                .requestMatchers("/admin/company-info/export").permitAll()
                .requestMatchers("/api/holidays/export").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/login", "/css/**", "/js/**").permitAll()
                .requestMatchers("/api/holidays/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf
            	.ignoringRequestMatchers("/api/holidays/**")   
                // 勤怠CSV出力エンドポイントをCSRF除外
                .ignoringRequestMatchers("/admin/export-attendance")
                // 祝日CSV出力エンドポイントもCSRF除外
                .ignoringRequestMatchers("/admin/company-info/export")
                .ignoringRequestMatchers("/api/holidays/export")
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(new CustomAuthenticationSuccessHandler())
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll()
            )
            .exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler));

        return http.build();
    }
}