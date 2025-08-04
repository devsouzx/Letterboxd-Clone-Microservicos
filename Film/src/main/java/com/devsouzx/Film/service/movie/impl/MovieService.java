package com.devsouzx.Film.service.movie.impl;

import com.devsouzx.Film.client.ITMDbClient;
import com.devsouzx.Film.database.model.Movie;
import com.devsouzx.Film.dto.movie.MovieResponse;
import com.devsouzx.Film.dto.tmdb.TMDbFilmResponse;
import com.devsouzx.Film.mapper.FilmMapper;
import com.devsouzx.Film.repository.movie.MovieRepository;
import com.devsouzx.Film.service.movie.IMovieService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieService implements IMovieService {
    @Value("${tmdb.apiKey}")
    private String apiKey;
    private final ITMDbClient client;
    private final FilmMapper mapper;
    private final MovieRepository movieRepository;

    public MovieService(FilmMapper mapper, ITMDbClient client, MovieRepository movieRepository) {
        this.mapper = mapper;
        this.client = client;
        this.movieRepository = movieRepository;
    }

    public MovieResponse getMovieByTmdbID(Integer tmdbID) {
        TMDbFilmResponse movieResponse = client.getMovieDetailsById(apiKey, tmdbID);
        return mapper.toMovieResponseFromTmdb(movieResponse);
    }

    public MovieResponse getOrSaveMovieByTmdbID(Integer tmdbID) {
        Optional<Movie> optional = movieRepository.getByTmdbID(tmdbID);

        if (optional.isEmpty()) {
            return saveFromTmdb(tmdbID);
        }

        Movie movie = optional.get();

        if(movie.isStale()) {
            return updateFromTmdb(movie.getIdentifier());
        }

        return mapper.toMovieResponse(movie);
    }

    public MovieResponse updateFromTmdb(UUID identifier) {
        Movie movie = movieRepository.getByIdentifier(identifier).orElseThrow(() -> new RuntimeException("Filme não encontrado no banco de dados."));

        MovieResponse updatedData = getMovieByTmdbID(movie.getTmdbID());

        Movie updatedMovie = mapper.toMovie(updatedData);
        updatedMovie.setIdentifier(movie.getIdentifier());
        updatedMovie.setCreated(movie.getCreated());
        updatedMovie.setLastUpdated(LocalDateTime.now());

        updatedMovie = movieRepository.save(updatedMovie);

        return mapper.toMovieResponse(updatedMovie);
    }

    public MovieResponse saveFromTmdb(Integer tmdbID) {
        MovieResponse movieResponse = getMovieByTmdbID(tmdbID);

        Movie movie = mapper.toMovie(movieResponse);

        movie.setCreated(LocalDateTime.now());
        movie.setLastUpdated(LocalDateTime.now());

        movie = movieRepository.save(movie);

        return mapper.toMovieResponse(movie);
    }
}
