package com.riyawansilver.security;

import com.riyawansilver.models.Role;
import com.riyawansilver.models.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetail implements UserDetails {

    private String username;
    private String password;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean accountNonLocked;
    private Collection<CustomGrantedAuthorities> authorities;
    private long userID;
    public CustomUserDetail() {
    }

    // private User user;
    public CustomUserDetail(User user){
        // this.user=user;
        this.username=user.getEmail();
        this.password=user.getPassword();
        this.accountNonLocked=true;
        this.accountNonExpired=true;
        this.credentialsNonExpired=true;
        this.enabled=true;
        this.userID=user.getId();


        List<CustomGrantedAuthorities> grantedAuthorities=new ArrayList<>();

        for(Role role: user.getRoles()){
            grantedAuthorities.add(new CustomGrantedAuthorities(role));
        }
        this.authorities= grantedAuthorities;


    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;

    }

    @Override
    public boolean isEnabled() {
//        return true;
        return enabled ;
    }

    public long getUserID() {
        return userID;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isAccountNonExpired() {
        //  return true;
        return accountNonExpired;
    }

    @Override
    public String getPassword() {
        //return user.getHashedPassword();
        return password;
    }

    @Override
    public String getUsername() {
        //return user.getEmail();
        return username;
    }
}
