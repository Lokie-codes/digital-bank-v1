package com.digitalbank.accounts_service.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity @Data
@Table(name = "users")
public class User {
    public User(Long id, String email, String firstName, String lastName, String phone, String username, String password) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phone = phone;
    }
    public User() {}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String username;
    private String password;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}
