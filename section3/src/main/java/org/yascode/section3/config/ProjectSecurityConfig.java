package org.yascode.section3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/notices", "/contact", "/error").permitAll()
                .requestMatchers("/login", "/css/**").permitAll()
                .anyRequest().authenticated());

        http.formLogin(fl -> fl.loginPage("/login"));
        http.httpBasic(withDefaults());

        return http.build();
    }
}
