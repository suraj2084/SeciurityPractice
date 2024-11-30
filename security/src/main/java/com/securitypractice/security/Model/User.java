package com.securitypractice.security.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Integer id;
    @Column(name = "user_name", nullable = false)
    public String userName;
    @Column(name = "password", nullable = false)
    public String password;
    @Column(name = "role", nullable = false)
    private String role;

}
