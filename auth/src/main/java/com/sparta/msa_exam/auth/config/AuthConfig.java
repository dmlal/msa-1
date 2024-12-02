package com.sparta.msa_exam.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // csrf 추가 설정 없음.  그래서 이렇게 함
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/signIn", "/auth/signUp", "/auth/").permitAll()
                        .requestMatchers("/auth/changeRoles/**").hasRole("ADMIN")
                        .anyRequest().denyAll()   // 인증된 유저는 접근 가능이 아닌 모두 접근 금지.
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

}
