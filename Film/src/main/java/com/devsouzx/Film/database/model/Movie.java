package com.devsouzx.Film.database.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movies")
public class Movie {
    @Id
    @Column(name = "identifier", length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID identifier;
    private String title;
    private String releaseYear;
    private String synopsis;
    private String tagline;
    private Integer runtime;
    private String country;
    private String primaryLanguage;
    private String trailerUrl;
    private String posterUrl;
    private String backdropUrl;

    @Column(unique = true, nullable = false)
    private Integer tmdbID;

    private List<String> spokenLanguages;
    private List<String> alternativeTitles;
    private List<String> studios;
    private List<String> genres;
    private List<String> themes;
    private List<String> whereToWatch;

    @OneToMany(mappedBy = "movies", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActorRole> cast;

    @OneToMany(mappedBy = "movies", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CrewMember> crew;

    @OneToMany(mappedBy = "movies", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReleaseInfo> releases;
}
