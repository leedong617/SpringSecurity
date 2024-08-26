package com.ex.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	// 패스워드 암호화
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	// 계층 권한 메소드
	@Bean
	public RoleHierarchy roleHierarchy() {

	    RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
	    // ADMIN > MAGAGER > USER 
	    // ADMIN은 MANAGER권한 접근가능
	    // MANAGER는 USER권한 접근가능
	    hierarchy.setHierarchy("ROLE_ADMIN > ROLE_MAMAGER\n" +
	            "ROLE_MAMAGER > ROLE_USER");

	    return hierarchy;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		/* requestMathcers 해당 url로 요청될시
		 * permitAll 모두 허용
		 * hasRole 1개의 사용자만 허용
		 * hasAnyRole 명시된 사용자 모두 허용
		 * anyRequest requestMatchers에 해당되지 않는 모든 uri 가장 마지막에 설정 되어야함
		 * */
		http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/login","/","/join").permitAll()
                .requestMatchers("/manager").hasAnyRole("MANAGER")
                .requestMatchers("/admin").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
        );
		// 로그인 설정
		http.formLogin((auth) -> auth
				.loginPage("/login") // login이 필요한 페이지 이동시 login 안되있을시 login페이지가 뜨도록
				.loginProcessingUrl("/loginProc")
				.permitAll()
				); // login페이지에서 loginForm 의 데이터가 해당 url로 넘겨져 login실행
		//로그아웃 설정
	    http
        .logout((auth) -> auth.logoutUrl("/logout")
                .logoutSuccessUrl("/"));
		
//		http.csrf((auth) -> auth
//				.disable()
//				);
		
		// 동시 접속(중복 로그인) 세션 개수 제한
		http.sessionManagement((auth) -> auth
        		// maximumSession(정수) : 하나의 아이디에 대한 다중 로그인 허용 개수
                .maximumSessions(1)
                // maxSessionPreventsLogin(boolean) : 다중 로그인 개수를 초과하였을 경우 처리 방법
                // true : 초과시 새로운 로그인 차단
                // false : 초과시 기존 세션 하나 삭제
                .maxSessionsPreventsLogin(true));
		
		// 세션 고정 공격을 보호하기 위한 로그인 성공시 세션 설정
		http.sessionManagement((auth) -> auth
        		/*
        		 * sessionManagement().sessionFixation().none() : 로그인 시 세션 정보 변경 안함
				   sessionManagement().sessionFixation().newSession() : 로그인 시 세션 새로 생성
				   sessionManagement().sessionFixation().changeSessionId() : 로그인 시 동일한 세션에 대한 id 변경
        		 */
                .sessionFixation().changeSessionId());
		
		return http.build();
	}
	
}
