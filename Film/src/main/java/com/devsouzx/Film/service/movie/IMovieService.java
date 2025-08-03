package com.devsouzx.Film.service.movie;

import com.devsouzx.Film.dto.movie.MovieResponse;

import java.util.UUID;

public interface IMovieService {
    public MovieResponse getMovieByTmdbID(Integer id);
}
