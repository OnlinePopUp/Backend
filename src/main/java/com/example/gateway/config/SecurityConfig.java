package com.example.gateway.config;

import com.example.gateway.jwt.JwtFilter;
import com.example.gateway.jwt.JwtUtil;
import com.example.gateway.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.cors(Customizer.withDefaults());

        http.csrf(csrf -> csrf.disable());

        http.securityContextRepository(NoOpServerSecurityContextRepository.getInstance());

        http.authorizeExchange(auth -> auth
                .pathMatchers("/auth/login","/auth/join", "auth/reissue","/").permitAll()
                .pathMatchers("/user/admin").hasRole("ROLE_ADMIN")
                .anyExchange().authenticated());

        http.addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }
}
