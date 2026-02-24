package com.miaoubich.security;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/keycloak/v1/demo")
public class KeycloakController {

    @GetMapping("/user")
    @PreAuthorize("hasRole('client_user')")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello from Keycloak --> Client-USER");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('client_admin')")
//    @PreAuthorize("hasAnyRole('client_admin','super_admin')")
    public ResponseEntity<?> demo() {
        return ResponseEntity.ok("Keycloak integration is successful --> Client-ADMIN");
    }

    @GetMapping("/super-user")
    @PreAuthorize("hasAnyRole('client_user')")
    public ResponseEntity<String> hello2() {
        return ResponseEntity.ok("Hello from Keycloak --> SUPER-USER");
    }
}
