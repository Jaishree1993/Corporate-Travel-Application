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
    .requestMatchers(
    "/", "/loginpage.html", "/booking-form.html", "/css/**", "/images/**", "/js/**",
    "/login", "/register", "/api/search/**", "/api/book","/bookings","/register.html"
).permitAll()

    .anyRequest().authenticated()
)

           /* .formLogin(form -> form
    .loginPage("/loginpage.html")
    .defaultSuccessUrl("/", true) // ✅ Redirect to controller that serves index.html
    .permitAll()
)*/
            .formLogin(form -> form.disable())
            .logout(logout -> logout.permitAll());

        return http.build();
    }
}
