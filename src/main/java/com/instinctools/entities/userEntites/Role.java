package com.instinctools.entities.userEntites;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    DEVELOPER, EMPLOYEE, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
