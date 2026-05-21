package org.autosalon.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public class SecurityUtils {

    public static UUID getCurrentUserId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var jwt = (Jwt) auth.getPrincipal();
        return UUID.fromString(jwt.getSubject());
    }

    public static boolean hasRole(String role) {
        var context = SecurityContextHolder.getContext();
        var authentification = context.getAuthentication();
        var authorities = authentification.getAuthorities();
        return authorities.stream().anyMatch(autority -> autority.getAuthority().equals("ROLE_" + role));
    }
}