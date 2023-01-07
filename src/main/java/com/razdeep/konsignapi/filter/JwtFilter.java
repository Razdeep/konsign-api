package com.razdeep.konsignapi.filter;

import com.razdeep.konsignapi.service.JwtUtilService;
import com.razdeep.konsignapi.service.KonsignUserDetailsService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private static final String BEARER_KEYWORD = "Bearer ";


    private final JwtUtilService jwtUtilService;
    private final KonsignUserDetailsService konsignUserDetailsService;

    @Autowired
    public JwtFilter(JwtUtilService jwtUtilService, KonsignUserDetailsService konsignUserDetailsService) {
        this.jwtUtilService = jwtUtilService;
        this.konsignUserDetailsService = konsignUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeaderStr = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeaderStr == null || !authorizationHeaderStr.startsWith(BEARER_KEYWORD)) {
            filterChain.doFilter(request, response);
            return;
        }

        String extractedJwtToken = authorizationHeaderStr.substring(BEARER_KEYWORD.length());
        String extractedUsername = jwtUtilService.extractUsername(extractedJwtToken);

        UserDetails konsignUserDetails = konsignUserDetailsService.loadUserByUsername(extractedUsername);

        if (konsignUserDetails == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!jwtUtilService.validateToken(extractedJwtToken, konsignUserDetails)) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(konsignUserDetails, null,
                konsignUserDetails.getAuthorities());

        usernamePasswordAuthenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }
}
