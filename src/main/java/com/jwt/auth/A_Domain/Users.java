package com.jwt.auth.A_Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", length = 150, nullable = false)
    private String name;
    @Column(name = "nickname", length = 200, nullable = false, unique = true)
    private String nickname;
    @Column(name = "password", length = 200, nullable = false)
    private String password;
    @Column(name = "email", length = 300, nullable = false, unique = true)
    private String email;
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

}
