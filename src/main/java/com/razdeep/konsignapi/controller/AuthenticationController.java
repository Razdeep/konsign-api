package com.razdeep.konsignapi.controller;

import com.razdeep.konsignapi.exception.UsernameAlreadyExists;
import com.razdeep.konsignapi.model.AuthenticationRequest;
import com.razdeep.konsignapi.model.AuthenticationResponse;
import com.razdeep.konsignapi.model.UserRegistration;
import com.razdeep.konsignapi.service.AuthenticationService;
import com.razdeep.konsignapi.service.JwtUtilService;
import com.razdeep.konsignapi.service.KonsignUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private KonsignUserDetailsService konsignUserDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Username or password mismatch");
        } catch (Exception e) {
            e.printStackTrace();
        }

        final UserDetails konsignUserDetails = konsignUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtilService.generateToken(konsignUserDetails);
        final AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setJwt(jwt);
        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody UserRegistration userRegistration) throws Exception {
        try {
            authenticationService.register(userRegistration);
        } catch (UsernameAlreadyExists e) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        return ResponseEntity.ok("Successfully registered");
    }
}
