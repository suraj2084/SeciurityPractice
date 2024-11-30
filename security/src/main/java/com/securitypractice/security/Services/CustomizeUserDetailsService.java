package com.securitypractice.security.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.securitypractice.security.Model.User;
import com.securitypractice.security.Repository.UserRepo;

@Service
public class CustomizeUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found" + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                AuthorityUtils.createAuthorityList(user.getRole()));
    }

}
