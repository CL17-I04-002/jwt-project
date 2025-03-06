package com.jwt.auth.A_Domain;

import com.jwt.auth.A_Domain.util.Role;
import jakarta.persistence.*;
import lombok.Builder;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;


@Entity
@Table(name = "users")
@Builder
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", length = 150, nullable = false)
    private String name;
    @Column(name = "username", length = 200, nullable = false, unique = true)
    private String username;
    @Column(name = "password", length = 200, nullable = false)
    private String password;
    @Column(name = "email", length = 300, nullable = true, unique = true)
    private String email;
    @Column(name = "enabled", nullable = true)
    private Boolean enabled;
    @Enumerated(EnumType.STRING)
    private Role role;

    public Users() {
    }

    public Users(Long id, String name, String username, String password, String email, Boolean enabled, Role role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
        this.role = role;
    }

    /**
     * Goes through the list of authorities
     * @return Collection<? extends GrantedAuthority>
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(Objects.isNull(role)) return null;

        if(Objects.isNull(role.getPermissions())) return null;

        return role.getPermissions().stream()
                .map(each -> each.name())
                .map(each -> new SimpleGrantedAuthority(each))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
