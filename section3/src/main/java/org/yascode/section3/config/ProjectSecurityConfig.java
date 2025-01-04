package org.yascode.section3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/notices", "/contact", "/error").permitAll()
                .anyRequest().authenticated());

        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user")
                .password("{noop}yascode")
                .authorities("read")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password("{bcrypt}$2a$12$aE7ej/tUVJYbYPM3xxkYF.8TNID..jXBQ5Mui03AiROWvQYs/kVfy")
                .authorities("admin")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

}
