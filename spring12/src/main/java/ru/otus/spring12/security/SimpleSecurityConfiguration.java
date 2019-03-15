package ru.otus.spring12.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;


@EnableWebFluxSecurity
@ComponentScan
public class SimpleSecurityConfiguration{

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        return http.csrf()
                .disable()
                .authorizeExchange()
                .pathMatchers("/*")
                .permitAll()
                .pathMatchers(HttpMethod.GET, "/api/books/**/*").hasRole("USER")
                .pathMatchers(HttpMethod.PUT, "/api/books").hasRole("USER")
                .pathMatchers(HttpMethod.POST, "/api/books").hasRole("USER")
                .pathMatchers(HttpMethod.DELETE, "/api/books/*").hasRole("ADMIN")
                .anyExchange()
                    .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                    .authenticationFailureHandler((exchange, exception) -> { exchange.getExchange().getResponse().setStatusCode(HttpStatus.BAD_REQUEST); return Mono.empty();})
                    .authenticationSuccessHandler((exchange, exception) -> Mono.empty())
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint((exchange, exception) -> { exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED); return Mono.empty();})
                    .accessDeniedHandler((exchange, exception) -> { exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN); return Mono.empty();})
                .and()
                .logout()
                .disable()
                .build();
    }
}