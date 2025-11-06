package com.example.AmazonClone.service;

import com.example.AmazonClone.models.Users;
import com.example.AmazonClone.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepo.findByEmail(username);
        if (user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("user not found "+username);
        }
        List<GrantedAuthority> authorities = user.getAuthorities().stream().map(authority -> new
                SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
        return new User(user.getEmail(), user.getPassword(), authorities);
    }
}
