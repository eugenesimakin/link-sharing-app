package org.linksharing.server.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(c -> c
                .requestMatchers(antMatcher("/register"))
                .permitAll()
                .requestMatchers(antMatcher("/api/check"))
                .permitAll()
                .anyRequest()
                .authenticated()
        );
        http.formLogin(c -> c
                .loginPage("/login")
                .permitAll()
        );
        return http.build();
    }
}