package com.devsouzx.Film.controller.movie;

import com.devsouzx.Film.dto.movie.MovieResponse;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface IMovieController {
    ResponseEntity<MovieResponse> getMovieByTmdbID(Integer id);
}
