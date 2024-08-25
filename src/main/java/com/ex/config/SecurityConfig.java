package com.ex.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		/* requestMathcers 해당 url로 요청될시
		 * permitAll 모두 허용
		 * hasRole 1개의 사용자만 허용
		 * hasAnyRole 명시된 사용자 모두 허용
		 * anyRequest requestMatchers에 해당되지 않는 모든 uri 가장 마지막에 설정 되어야함
		 * */
		http.authorizeHttpRequests(
				(auth) -> auth
				.requestMatchers("/", "/login", "/loginProc", "/join", "/joinProc").permitAll()
				.requestMatchers("/admin").hasRole("ADMIN")
				.requestMatchers("/my/**").hasAnyRole("ADMIN","USER")
				.anyRequest().authenticated()
				);
		
		http.formLogin(
				(auth) -> auth
				.loginPage("/login") // login이 필요한 페이지 이동시 login 안되있을시 login페이지가 뜨도록
				.loginProcessingUrl("/loginProc")
				.permitAll()
				); // login페이지에서 loginForm 의 데이터가 해당 url로 넘겨져 login실행
		
		http.csrf((auth) -> auth
				.disable()
				);
		
		return http.build();
	}
	
}
