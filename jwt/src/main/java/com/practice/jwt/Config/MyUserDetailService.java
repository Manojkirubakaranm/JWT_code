package com.practice.jwt.Config;

import com.practice.jwt.Model.Users;
import com.practice.jwt.repo.UserRepo;
import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user =  repo.findByUsername(username);
        System.out.println("@UserDetails = "+user.getUsername() );
        return new UserPrincipal(user);
    }
}
