package com.riyawansilver.security;

import com.riyawansilver.models.Role;
import org.springframework.security.core.GrantedAuthority;

public class CustomGrantedAuthorities implements GrantedAuthority {

    private Role role;

    public CustomGrantedAuthorities(Role role){
        this.role=role;
    }

    @Override
    public String getAuthority() {
        return role.getName();
    }
}
