package com.flyai.safet.entity;

import lombok.*;
import javax.persistence.*;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "`user`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "users_id")
    private Long id;

    @Column(name = "username", length = 50)
    private String username;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "age", length = 50)
    private String age;

    @Column(name = "gender", length = 50)
    private String gender;

    @Column(name = "vehicle_type", length = 50)
    private String vehicleType;

    @Column(name = "vehicle_number", length = 200)
    private String vehicleNumber;

    @Column(name = "password", length = 100)
    private String password;

}