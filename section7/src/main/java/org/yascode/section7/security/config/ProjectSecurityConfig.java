package org.yascode.section7.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.yascode.section7.security.entrypoint.CustomBasicAuthenticationEntryPoint;
import org.yascode.section7.security.handler.CustomAccessDeniedHandler;
import org.yascode.section7.security.handler.CustomAuthenticationFailureHandler;
import org.yascode.section7.security.handler.CustomAuthenticationSuccessHandler;

@Configuration
@Profile("!prod")
@RequiredArgsConstructor
public class ProjectSecurityConfig {
    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(smc -> smc.invalidSessionUrl("/invalidSession")
                                         .sessionFixation(session -> session.newSession())
                                         .maximumSessions(1)
                                         .maxSessionsPreventsLogin(true)
                                         .expiredUrl("/expiredSession"))
            .requiresChannel(rcc -> rcc.anyRequest().requiresInsecure())
            .csrf(csrfConfigurer -> csrfConfigurer.disable())
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers( "/", "/home","/login/**", "/register", "/notices", "/contact",
                                  "/assets/**", "/error", "/invalidSession", "expiredSession").permitAll()
                .anyRequest().authenticated())
            .formLogin(flc -> flc.loginPage("/login")
                                 .usernameParameter("userid")
                                 .passwordParameter("secretPwd")
                                 .successHandler(authenticationSuccessHandler)
                                 .failureHandler(authenticationFailureHandler))
            .logout(lc -> lc.logoutSuccessUrl("/login?logout=true")
                            .invalidateHttpSession(true)
                            .clearAuthentication(true)
                            .deleteCookies("JSESSIONID"))
            .httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()))
            .exceptionHandling(ex -> ex.accessDeniedHandler(new CustomAccessDeniedHandler()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

}
