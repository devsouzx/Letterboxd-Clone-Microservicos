package com.devsouzx.Film.database.model;

import java.time.Duration;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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

    @Column(name = "title")
    private String title;

    @Column(name = "release_year")
    private String releaseYear;

    @Column(name = "synopsis", columnDefinition = "TEXT")
    private String synopsis;

    @Column(name = "tagline")
    private String tagline;

    @Column(name = "runtime")
    private Integer runtime;

    @Column(name = "country")
    private String country;

    @Column(name = "primary_language")
    private String primaryLanguage;

    @Column(name = "trailer_url")
    private String trailerUrl;

    @Column(name = "poster_url")
    private String posterUrl;

    @Column(name = "backdrop_url")
    private String backdropUrl;

    @Column(name = "tmdb_id", unique = true, nullable = false)
    private Integer tmdbID;

    @ElementCollection
    @CollectionTable(name = "movie_spoken_languages", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "language")
    private List<String> spokenLanguages;

    @ElementCollection
    @CollectionTable(name = "movie_alternative_titles", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "title")
    private List<String> alternativeTitles;

    @ElementCollection
    @CollectionTable(name = "movie_studios", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "studio")
    private List<String> studios;

    @ElementCollection
    @CollectionTable(name = "movie_genres", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "genre")
    private List<String> genres;

    @ElementCollection
    @CollectionTable(name = "movie_themes", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "theme")
    private List<String> themes;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "slug")
    private String slug;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActorRole> cast;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CrewMember> crew;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReleaseInfo> releases;

    public Boolean isStale() {
        if (updatedAt == null) return true;
        return Duration.between(updatedAt, LocalDateTime.now()).toDays() >= 5;
    }
}
