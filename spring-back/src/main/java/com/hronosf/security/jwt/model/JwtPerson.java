package com.hronosf.security.jwt.model;

import com.hronosf.model.entity.Person;
import com.hronosf.model.entity.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtPerson implements UserDetails {

    private final Long id;
    private final String userName;
    private final String password;
    private final String email;
    private final Date lastLoginTime;
    private final Date lastPasswordResetDate;
    private final boolean isActivated;
    private final Collection<? extends GrantedAuthority> authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActivated;
    }

    public JwtPerson(Person person) {
        this.id = person.getId();
        this.userName = person.getUserName();
        this.password = person.getPassword();
        this.email = person.getEmail();
        this.lastLoginTime = person.getLastLoginTime();
        this.lastPasswordResetDate = person.getUpdatedTimestamp();
        this.isActivated = person.isActivated();
        this.authorities = rolesToAuthorities(person.getRoles());
    }

    private List<GrantedAuthority> rolesToAuthorities(List<Roles> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}

