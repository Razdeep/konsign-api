package com.razdeep.konsignapi.service;

import com.razdeep.konsignapi.model.KonsignUserDetails;
import com.razdeep.konsignapi.repository.KonsignUserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KonsignUserDetailsService implements UserDetailsService {

    @Autowired
    private KonsignUserDetailsRepository konsignUserDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<KonsignUserDetails> konsignUserDetails = konsignUserDetailsRepository.findKonsignUserDetailsByUsername(username);
        if (konsignUserDetails.isPresent()) {
            return konsignUserDetails.get();
        }
        return null;
    }
}
