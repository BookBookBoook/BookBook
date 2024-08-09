package com.example.bookbook.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
  	
        http
	        	//csrf 보호
            	.csrf(csrf->csrf.disable())
					 
	
            
            //uri에 대한 보안
            .authorizeHttpRequests(authorize -> authorize
            		.requestMatchers("/**","/signup","/login/**","/bookList","/detail","/event").permitAll()
					/* .requestMatchers("/login/**","/oauth2/**").permitAll() */
          	.requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
            	.requestMatchers("/admin/**").hasRole("ADMIN") //admin으로 시작하는 주소는 'ADMIN' 권한이 필요하다는 의미
            	.requestMatchers("/Seller/**").hasRole("Seller")
              .anyRequest().authenticated() //위 설정을 제외한 나머지 요청은 인증 필요
            )
            
            //http 인증 설정 (기본 설정 사용)
            .httpBasic(Customizer.withDefaults())
            
            //form 로그인 설정 (기본 설정 사용)
            .formLogin(login -> login
          		  .loginPage("/login") //로그인 페이지로 이동하는 url
          		  .failureUrl("/login?error=true") //로그인 실패시 url
          		  .permitAll()
          		  .usernameParameter("email")
          		  .passwordParameter("password")
          		  .defaultSuccessUrl("/", true) 
          		  )
            
            //logout 설정
            .logout(logout -> logout
          		  .logoutSuccessUrl("/login") //로그아웃 시 로그인 페이지로
          		  .invalidateHttpSession(true)
          		  .deleteCookies("JSESSIONID")) //로그아웃 시 쿠키 삭제
            //GET 요청을 통해 로그아웃을 처리하도록 허용
            .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")))
            
            .userDetailsService(customUserDetailsService);
        	
        return http.build();
    }	
	 

}