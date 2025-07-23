package com.harsh.projectmanagementsystem.controller;

import com.harsh.projectmanagementsystem.config.JwtProvider;
import com.harsh.projectmanagementsystem.modal.User;
import com.harsh.projectmanagementsystem.repository.UserRepository;
import com.harsh.projectmanagementsystem.request.LoginRequest;
import com.harsh.projectmanagementsystem.response.AuthResponse;
import com.harsh.projectmanagementsystem.service.CustomeUserDetailImpl;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Remove if not needed:
    @Autowired
    private CustomeUserDetailImpl customUserDetails;

    @PostMapping("/SignUp")
    public ResponseEntity<?> createUserHandler(@RequestBody User user) throws Exception {
        User isUserExist = userRepository.findByEmail(user.getEmail());
        if (isUserExist != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists with another account");
        }

        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());
        newUser.setFullname(user.getFullname());
        userRepository.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse res = new AuthResponse();
        res.setMessage("signup success");
        res.setJwt(jwt);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    // put for the sign in logic here
    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest) {

        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse res = new AuthResponse();
        res.setMessage("signin success");
        res.setJwt(jwt);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }


    // helper function for validaion of the username and password is correct or not 
    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        if (userDetails == null) {

            throw new BadCredentialsException("Invalid username");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {

            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
    }

    // sign in logic ends here

}
