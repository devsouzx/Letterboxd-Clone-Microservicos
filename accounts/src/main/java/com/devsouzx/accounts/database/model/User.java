package com.devsouzx.accounts.database.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
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
}
