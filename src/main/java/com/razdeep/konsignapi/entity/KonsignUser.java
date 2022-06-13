package com.razdeep.konsignapi.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "User")
@Data
public class KonsignUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String password;
    private boolean active;
    private String roles;
}
