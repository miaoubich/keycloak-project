package com.miaoubich.security;

import com.miaoubich.keycloakutil.Constants;
import com.miaoubich.keycloakutil.KeycloakCommonMethods;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken>{

    //@Value("${keycloak.client.name}")
    private String clientName;
    //@Value("${keycloak.client.preferred-username}")
    private String preferredUsername;

    private final Logger logger = LoggerFactory.getLogger(JwtAuthConverter.class);
    private final KeycloakCommonMethods keycloakCommonMethods;

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
            new JwtGrantedAuthoritiesConverter();

    public JwtAuthConverter(KeycloakCommonMethods keycloakCommonMethods) {
        this.keycloakCommonMethods = keycloakCommonMethods;
    }

    @PostConstruct
    protected void init() {
        clientName = keycloakCommonMethods.getParamValueByParamName(Constants.APPLICATION_CLIENT_NAME);
        preferredUsername = keycloakCommonMethods.getParamValueByParamName(Constants.PREFERRED_USERNAME);
    }

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                Objects.requireNonNull(extractResourceRoles(jwt)).stream()).collect(Collectors.toSet());

        return new JwtAuthenticationToken(
                jwt,
                authorities,
                getPrincipleClaimName(jwt)
        );
    }

    private String getPrincipleClaimName(Jwt jwt) {
        // if I don't have a preferredUsername (principleAttribute) I use the SUB by default
        String claimName = JwtClaimNames.SUB;
        // otherwise I extract it as the following
        if(preferredUsername != null)
            claimName = preferredUsername;
        return jwt.getClaim(claimName).toString();
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> resourceAccess;
        Map<String, Object> resource;
        Collection<String> resourceRoles;

        if(jwt.getClaim("resource_access") == null)
            return Set.of();
        resourceAccess = jwt.getClaim("resource_access");

        if(resourceAccess.get(clientName) == null)
            return Set.of();

        resource = (Map<String, Object>) resourceAccess.get(clientName);

        resourceRoles = (Collection<String>) resource.get("roles");

        return resourceRoles
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }

}
