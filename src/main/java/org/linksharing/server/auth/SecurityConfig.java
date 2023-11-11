package org.linksharing.server.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests()
                .requestMatchers(antMatcher("/registration_page.html")).permitAll()
                .requestMatchers(antMatcher("/registerUser")).permitAll()
                .requestMatchers(antMatcher("/loginUser")).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login_page.html").permitAll()
                .and()
                .logout().permitAll();
        return http.build();
    }
}