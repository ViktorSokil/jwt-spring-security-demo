package com.sokil.service.impl;


import com.sokil.entity.Role;
import com.sokil.entity.UserEntity;
import com.sokil.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("authenticationService")
public class AuthenticationServiceImpl implements UserDetailsService{

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String name)
            throws UsernameNotFoundException {
        UserEntity user = userService.findByUserName(name);
        if(user==null){
            throw new UsernameNotFoundException("Username not found");
        }
        return new User(user.getUserName(),
                user.getUserPassword(),
                true, true, true, true,
                buildUserAuthority(user.getRoles()));
    }


    private List<GrantedAuthority> buildUserAuthority(Set<Role> userRoles) {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        // Build user's authorities
        for (Role userRole : userRoles) {
            setAuths.add(new SimpleGrantedAuthority("ROLE_"+userRole.getRole()));
        }

        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
        return Result;
    }
}
