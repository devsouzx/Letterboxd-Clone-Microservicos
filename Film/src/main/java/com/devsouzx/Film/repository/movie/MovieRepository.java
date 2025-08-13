package com.devsouzx.Film.repository.movie;

import com.devsouzx.Film.database.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {
    Optional<Movie> getByTmdbID(Integer tmdbID);
    Optional<Movie> getByIdentifier(UUID identifier);
    Optional<Movie> getMovieBySlug(String slug);
}
