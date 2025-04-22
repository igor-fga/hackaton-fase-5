package com.prontuario.pacientes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.GET, "/api/prescricoes/paciente/{id}").hasAnyRole("medico", "farmaceutico")
                        .requestMatchers(HttpMethod.GET, "/api/prescricoes/paciente/{id}/ativas").hasAnyRole("medico", "farmaceutico")
                        .requestMatchers(HttpMethod.GET, "/api/prescricoes").hasAnyRole("medico", "farmaceutico")
                        .requestMatchers(HttpMethod.POST, "/api/prescricoes").hasAnyRole("medico", "farmaceutico")
                        .requestMatchers(HttpMethod.GET, "/api/medicamentos/{id}").hasAnyRole("medico", "farmaceutico")
                        .requestMatchers(HttpMethod.GET, "/api/medicamentos").hasAnyRole("medico", "farmaceutico")
                        .requestMatchers(HttpMethod.GET, "/api/medicamentos/busca?nome={nome}").hasAnyRole("medico", "farmaceutico")
                        .anyRequest().hasRole("farmaceutico")
                )

                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );

        return http.build();
    }

    @Bean
    public Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        return new JwtAuthConverter();
    }
}