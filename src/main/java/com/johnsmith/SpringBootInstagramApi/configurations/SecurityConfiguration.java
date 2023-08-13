package com.johnsmith.SpringBootInstagramApi.configurations;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.johnsmith.SpringBootInstagramApi.security.JwtAuthenticationEntryPoint;
import com.johnsmith.SpringBootInstagramApi.security.JwtAuthenticationRequestFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	private final UserDetailsService userDetailsService;
    private final JwtAuthenticationRequestFilter jwtAuthenticationRequestFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("http://localhost:4200"));
		configuration.setAllowedMethods(List.of("GET","POST", "PUT", "PATCH", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
    }
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> {
                    authorize.antMatchers("/api/v1/auth/register", "/api/v1/auth/login").permitAll();
                    authorize.anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(this.authenticationProvider())
                .addFilterBefore(jwtAuthenticationRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .build();
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
