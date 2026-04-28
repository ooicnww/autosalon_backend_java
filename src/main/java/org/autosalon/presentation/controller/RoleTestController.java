package org.autosalon.presentation.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleTestController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/test/auth")
    public String testAuth() {
        return "authenticated ok";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/test/user")
    public String testUser() {
        return "user ok";
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/test/manager")
    public String testManager() {
        return "manager ok";
    }

    @PreAuthorize("hasRole('WAREHOUSE_ADMIN')")
    @GetMapping("/test/warehouse")
    public String testWarehouse() {
        return "warehouse ok";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/test/admin")
    public String testAdmin() {
        return "admin ok";
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/test/user-or-admin")
    public String testUserOrAdmin() {
        return "user or admin ok";
    }
}