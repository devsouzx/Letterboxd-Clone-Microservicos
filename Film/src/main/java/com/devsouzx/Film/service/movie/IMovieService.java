package com.devsouzx.Film.service.movie;

import com.devsouzx.Film.dto.movie.MovieResponse;

import java.util.UUID;

public interface IMovieService {
    public MovieResponse getMovieByTmdbID(Integer tmdbID);
    public MovieResponse getOrSaveMovieByTmdbID(Integer tmdbID);
    public MovieResponse saveFromTmdb(Integer tmdbID);
    MovieResponse updateFromTmdb(UUID identifier);
}
