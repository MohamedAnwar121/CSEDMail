package com.example.mailbe.Config;

import com.example.mailbe.Security.JwtTokenFilter;
import com.example.mailbe.Security.LoginFilter;
import com.example.mailbe.Security.UserAuthenticationProvider;
import com.example.mailbe.Service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;


@Configuration
public class SecurityConfig {

    UserService userService;
    UserAuthenticationProvider authProvider;

    @Lazy
    public SecurityConfig(UserService userService, UserAuthenticationProvider authProvider) {
        this.userService = userService;
        this.authProvider = authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        .antMatchers("/api/auth/*").permitAll()
                        .antMatchers("/api/mail/*").permitAll()
                        .antMatchers("/api/user/*").permitAll()
                        .antMatchers("/api/file/*").permitAll()
                )
                .csrf().disable()
                .cors().configurationSource(cors -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedMethods(Collections.singletonList("*"));
                    configuration.setAllowedHeaders(Collections.singletonList("*"));
                    configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
                    configuration.setAllowCredentials(true);
                    configuration.setMaxAge(2500L);
                    return configuration;
                }).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(new JwtTokenFilter(userService), LoginFilter.class)
                .httpBasic();
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authProvider);
        return authenticationManagerBuilder.build();
    }

}
