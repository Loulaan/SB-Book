package com.hronosf.security.jwt.service.impl;

import com.hronosf.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.hronosf.security.jwt.model.JwtPerson;

@RequiredArgsConstructor
public class JwtPersonDetailsService implements UserDetailsService {

    private final PersonService personService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new JwtPerson(personService.findByUserName(username));
    }
}
