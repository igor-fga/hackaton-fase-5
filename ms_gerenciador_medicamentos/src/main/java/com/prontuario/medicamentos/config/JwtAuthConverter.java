package com.prontuario.pacientes.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.*;
import java.util.stream.Collectors;

public class JwtAuthConverter implements Converter<Jwt, JwtAuthenticationToken> {

    private static final List<String> SUPPORTED_CLIENTS = Arrays.asList(
            "api-hospital", "api-medico", "api-paciente"
    );

    private final JwtGrantedAuthoritiesConverter defaultGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Override
    public JwtAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = new HashSet<>(defaultGrantedAuthoritiesConverter.convert(jwt));

        // Adiciona roles do realm_access
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess != null && realmAccess.containsKey("roles")) {
            Collection<String> roles = (Collection<String>) realmAccess.get("roles");
            authorities.addAll(roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toSet()));
        }

        // Adiciona roles de client_access (caso queira usar esse também)
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess != null) {
            for (String client : SUPPORTED_CLIENTS) {
                Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get(client);
                if (clientAccess != null && clientAccess.containsKey("roles")) {
                    Collection<String> roles = (Collection<String>) clientAccess.get("roles");
                    authorities.addAll(roles.stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                            .collect(Collectors.toSet()));
                }
            }
        }

        return new JwtAuthenticationToken(jwt, authorities);
    }
}
