package com.flyai.safet.entity;

import lombok.*;
import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;

    @Column(name = "username", length = 50)
    private String username;

    @Column(name = "password", length = 100)
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}