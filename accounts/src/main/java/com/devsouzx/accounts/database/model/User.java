package com.devsouzx.accounts.database.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @Column(name = "identifier", length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID identifier;

    @Column(name = "first_name", length = 255)
    private String firstname;

    @Column(name = "last_name", length = 255)
    private String lastname;

    @Column(name = "username", length = 255)
    private String username;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "biography", length = 255)
    private String biography;

    @Column(name = "location", length = 100)
    private String location;

    @Column(name = "site", length = 255)
    private String site;

    @Column(name = "pronoun", length = 50)
    private String pronoun;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "custom_posters")
    private Boolean customPosters;

    @Column(name = "adult_content")
    private Boolean adultContent;

    @Column(name = "include_profile_in_members")
    private Boolean includeProfileInMembers;

    @Column(name = "registration_time")
    private LocalDateTime registrationTime;

    @Column(name = "first_access")
    private Boolean firstAccess;

    @Column(name = "confirmed_email")
    private Boolean confirmedEmail;

    @Column(name = "confirmation_code")
    private String confirmationCode;

    @Column(name = "verified")
    private Boolean verified;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        USER, ADMIN
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
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
}
