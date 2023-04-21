package com.razdeep.konsignapi.controller;

import com.razdeep.konsignapi.exception.UsernameAlreadyExists;
import com.razdeep.konsignapi.model.AuthenticationRequest;
import com.razdeep.konsignapi.model.AuthenticationResponse;
import com.razdeep.konsignapi.model.UserRegistration;
import com.razdeep.konsignapi.service.AuthenticationService;
import com.razdeep.konsignapi.service.JwtUtilService;
import com.razdeep.konsignapi.service.KonsignUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

@CrossOrigin
@RestController
public class AuthenticationController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationManager authenticationManager;
    private final KonsignUserDetailsService konsignUserDetailsService;
    private final JwtUtilService jwtUtilService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, KonsignUserDetailsService konsignUserDetailsService, JwtUtilService jwtUtilService, AuthenticationService authenticationService) {
        this.authenticationManager = authenticationManager;
        this.konsignUserDetailsService = konsignUserDetailsService;
        this.jwtUtilService = jwtUtilService;
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Username or password mismatch");
        } catch (Exception e) {
            e.printStackTrace();
        }

        final UserDetails konsignUserDetails = konsignUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String accessToken = jwtUtilService.generateToken(konsignUserDetails);
        final AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAccessToken(accessToken);

        Cookie cookie = new Cookie("refresh-token", jwtUtilService.doGenerateRefreshToken(new HashMap<>(), authenticationRequest.getUsername()));

        cookie.setMaxAge(7 * 24 * 60 * 60);

//        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);
        return ResponseEntity.ok(authenticationResponse);
    }

    @GetMapping(value = "/refreshtoken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {

        if (request.getCookies() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<String> refreshTokenOptional = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("refresh-token"))
                .map(Cookie::getValue)
                .findAny();

        if (refreshTokenOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        String refreshToken = refreshTokenOptional.get();
        LOG.info(String.format("refresh token: %s", refreshToken));

        try {
            if (jwtUtilService.validateToken(refreshToken, null)) {
//                jwtUtilService.validateToken(jwtUtilService.extractAccessTokenFromRequest(request), null);
                DefaultClaims claims = (DefaultClaims) request.getAttribute("claims");
                val claimsMap = jwtUtilService.getMapFromIoJsonWebTokenClaims(claims);
                String jwtToken = jwtUtilService.doGenerateRefreshToken(claimsMap, (String) claimsMap.get("sub"));
                AuthenticationResponse authenticationResponse = new AuthenticationResponse();
                authenticationResponse.setAccessToken(jwtToken);
                return ResponseEntity.ok(authenticationResponse);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody UserRegistration userRegistration) {
        try {
            authenticationService.register(userRegistration);
        } catch (UsernameAlreadyExists e) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        return ResponseEntity.ok("Successfully registered");
    }
}
