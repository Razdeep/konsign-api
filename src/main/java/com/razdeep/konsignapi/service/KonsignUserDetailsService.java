package com.razdeep.konsignapi.service;

import com.razdeep.konsignapi.model.KonsignUser;
import com.razdeep.konsignapi.model.KonsignUserDetails;
import com.razdeep.konsignapi.repository.KonsignUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KonsignUserDetailsService implements UserDetailsService {

    @Autowired
    private KonsignUserRepository konsignUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<KonsignUser> konsignUser = konsignUserRepository.findKonsignUserByUsername(username);
        if (!konsignUser.isPresent()) {
            throw new UsernameNotFoundException("user name not found");
        }
        return new KonsignUserDetails(konsignUser.get());
    }
}
