package com.beaconfire.springsecurityauth.controller;

import com.beaconfire.springsecurityauth.domain.request.LoginRequest;
import com.beaconfire.springsecurityauth.domain.response.LoginResponse;
import com.beaconfire.springsecurityauth.dto.CustomUserDetail;
import com.beaconfire.springsecurityauth.security.AuthUserDetail;
import com.beaconfire.springsecurityauth.security.JwtProvider;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;


@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
//    @Autowired
//    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//
//    }
    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private JwtProvider jwtProvider;

    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    //User trying to log in with username and password
    @PostMapping("auth/login")
    public LoginResponse login(@RequestBody LoginRequest request){

        System.out.println(request.getUsername());

        Authentication authentication;

        //Try to authenticate the user using the username and password
        try{
          authentication = authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
          );
        } catch (AuthenticationException e){
            System.out.println("Provided credential is invalid." + e.getMessage());
            throw new BadCredentialsException("Provided credential is invalid.");
        }

        //Successfully authenticated user will be stored in the authUserDetail object
        CustomUserDetail authUserDetail = (CustomUserDetail) authentication.getPrincipal(); //getPrincipal() returns the user object
        System.out.println(authUserDetail.getUsername());

        //A token wil be created using the username/email/userId and permission
        String token = jwtProvider.createToken(authUserDetail);
        System.out.println(token);
        //Returns the token as a response to the frontend/postman
        return LoginResponse.builder()
                .message("Welcome " + authUserDetail.getUsername())
                .token(token)
                .build();

    }

}
