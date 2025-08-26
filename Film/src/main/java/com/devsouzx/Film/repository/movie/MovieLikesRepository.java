package com.devsouzx.Film.repository.movie;

import com.devsouzx.Film.database.model.MovieLikes;
import com.devsouzx.Film.database.model.MoviesLikesId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MovieLikesRepository extends JpaRepository<MovieLikes, MoviesLikesId> {
    Optional<List<MovieLikes>> findByIdMovieIdentifier(UUID movieId);
    Optional<MovieLikes> findAllByIdMovieIdentifierAndIdUserIdentifier(UUID movieIdentifier, UUID userIdentifier);
}
