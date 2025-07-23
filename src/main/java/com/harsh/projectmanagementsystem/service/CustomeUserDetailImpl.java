package com.harsh.projectmanagementsystem.service;

import java.util.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.harsh.projectmanagementsystem.modal.User;
import com.harsh.projectmanagementsystem.repository.UserRepository;


@Service
public class CustomeUserDetailImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override 
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
         
        User user = userRepository.findByEmail(username);

        if(user == null){
            throw new UsernameNotFoundException("user not found email" + user);
        }

      List<GrantedAuthority> authorities = new ArrayList<>();






        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }

}
