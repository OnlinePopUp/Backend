package com.example.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.cors(Customizer.withDefaults());

        http.csrf(csrf -> csrf.disable());

        http.securityContextRepository(NoOpServerSecurityContextRepository.getInstance());

        http.authorizeExchange(auth -> auth
                .pathMatchers("/").permitAll()
                .pathMatchers("/user/admin").hasRole("ROLE_ADMIN")
                .anyExchange().authenticated());


        return http.build();
    }
}
