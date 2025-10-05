package com.example.corporateapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/loginpage.html", "/css/**", "/images/**", "/js/**", "/login", "/register").permitAll()

                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/loginpage.html")
                .defaultSuccessUrl("/flights", true) // optional: redirect after login
                .permitAll()
            )
            .logout(logout -> logout.permitAll());

        return http.build();
    }
}
