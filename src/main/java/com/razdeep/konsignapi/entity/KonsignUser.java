package com.razdeep.konsignapi.entity;

import com.razdeep.konsignapi.model.UserRegistration;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "User")
@Data
public class KonsignUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String username;
    private String password;
    private String emailAddress;
    private String mobile;
    private boolean active;
    private String roles;
    public KonsignUser() {}

    public KonsignUser(UserRegistration userRegistration) {
        username = userRegistration.getUsername();
        password = userRegistration.getPassword();
        emailAddress = userRegistration.getEmailAddress();
        mobile = userRegistration.getMobile();
        active = true;
        roles = "ROLE_USER";
    }
}
