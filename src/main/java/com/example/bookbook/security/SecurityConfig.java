package com.example.bookbook.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
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
	
	private final CustomUserDetailsService customUserDetailsService;
    private final CustomLoginSuccessHandler customLoginSuccessHandler;

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
                .requestMatchers("/admin/**", "/seller/**").hasRole("SELLER")
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
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")))
            .userDetailsService(customUserDetailsService);

        return http.build();
    }
}