package com.app.reactive.ws.users.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity // Enables spring security in WebFlux applications
public class WebSecurity {
    
    @Bean // Java bean that will be managed by spring container, will be available for dependency injection in other spring components
    SecurityWebFilterChain httpSecurityWebFilterChain(ServerHttpSecurity http, ReactiveAuthenticationManager authenticationManager) {
        return http
        .authorizeExchange(
            exchanges -> exchanges
                .pathMatchers(HttpMethod.POST, "/users").permitAll() // Users endpoint able to be access
                .pathMatchers(HttpMethod.POST, "/login").permitAll()
            .anyExchange().authenticated()) // Other must be authenticated
            .csrf(ServerHttpSecurity.CsrfSpec::disable) // Not needed since we're creating RestFull services and not stateless service
            // .httpBasic(httpBasic -> httpBasic.disable())
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable) // This is used to disable the authentication through HTTP headers, instead an user should be logged via the login API endpoint 
            .authenticationManager(authenticationManager)         // Instead we specify the authentication manager we are going to use
            .build();
    }

    @Bean 
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
