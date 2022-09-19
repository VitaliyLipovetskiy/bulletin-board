package com.lvv.bulletinboard.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Vitalii Lypovetskyi
 */
public enum Role implements GrantedAuthority {
    USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
