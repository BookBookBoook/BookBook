package com.example.bookbook.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	UsernamePasswordAuthenticationToken a;
	
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomLoginSuccessHandler customLoginSuccessHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    
    @Bean
    @Order(1)
    public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/login/admin/**")
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll()
            )
            .formLogin(login -> login
                .loginPage("/login/admin")
                .loginProcessingUrl("/login/admin")
                .failureUrl("/login/admin?error=true")
                .usernameParameter("businessNum")
                .passwordParameter("password")
                .successHandler(customLoginSuccessHandler)
                .permitAll()
            );
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/signup", "/login/**", "/bookList", "/detail", "/event").permitAll()
                .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/seller/**").hasRole("SELLER")
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .failureUrl("/login?error=true")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(customLoginSuccessHandler)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // 로그아웃 URL 설정
                .logoutSuccessUrl("/login") // 로그아웃 성공 후 리디렉션 URL
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .userDetailsService(customUserDetailsService);
        
		http
		.oauth2Login(oauth2->oauth2
				.loginPage("/login")
				.defaultSuccessUrl("/additional-info", true)
				)
					;
        
        return http.build();
    }
    
    
}
