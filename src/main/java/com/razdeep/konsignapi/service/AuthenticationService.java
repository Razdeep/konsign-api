package com.razdeep.konsignapi.service;

import com.razdeep.konsignapi.config.SecurityConfig;
import com.razdeep.konsignapi.entity.KonsignUser;
import com.razdeep.konsignapi.model.UserRegistration;
import com.razdeep.konsignapi.repository.KonsignUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private KonsignUserRepository konsignUserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean register(UserRegistration userRegistration) {
        KonsignUser konsignUser = new KonsignUser(userRegistration);
        konsignUser.setPassword(bCryptPasswordEncoder.encode(konsignUser.getPassword()));
        return konsignUserRepository.save(konsignUser) != null;
    }
}
