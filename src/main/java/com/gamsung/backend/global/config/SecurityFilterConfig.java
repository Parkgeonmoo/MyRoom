package com.gamsung.backend.global.config;

import com.gamsung.backend.global.jwt.security.JwtExceptionFilter;
import com.gamsung.backend.global.jwt.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;

@Configuration
@RequiredArgsConstructor
public class SecurityFilterConfig {

    private final CorsConfigurationSource corsConfigurationSource;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain http(HttpSecurity http) throws Exception {
        http
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()

                                .requestMatchers(HttpMethod.POST, "/v1/member/logout", "/v1/member/refresh").authenticated()

                                .requestMatchers(HttpMethod.GET, "/v1/order/me").authenticated()
                                .requestMatchers(HttpMethod.POST, "/v1/order").authenticated()

                                .requestMatchers(HttpMethod.GET, "/v1/cart").authenticated()
                                .requestMatchers(HttpMethod.POST, "/v1/cart").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/v1/cart").authenticated()

                                .anyRequest().permitAll()
                );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtExceptionFilter, JwtFilter.class);

        return http.build();
    }
}
