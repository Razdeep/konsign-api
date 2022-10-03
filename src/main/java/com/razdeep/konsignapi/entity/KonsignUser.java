package com.razdeep.konsignapi.entity;

import com.razdeep.konsignapi.model.UserRegistration;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "User")
@Getter
@Setter
public class KonsignUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "username", unique = true)
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
