package com.devsouzx.Film.service.movie.impl;

import com.devsouzx.Film.client.ITMDbClient;
import com.devsouzx.Film.database.model.Movie;
import com.devsouzx.Film.dto.movie.MovieResponse;
import com.devsouzx.Film.dto.tmdb.TMDbFilmResponse;
import com.devsouzx.Film.mapper.FilmMapper;
import com.devsouzx.Film.service.movie.IMovieService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MovieService implements IMovieService {
    @Value("${tmdb.apiKey}")
    private String apiKey;
    private ITMDbClient client;
    private FilmMapper mapper;

    public MovieService(FilmMapper mapper, ITMDbClient client) {
        this.mapper = mapper;
        this.client = client;
    }

    public MovieResponse getMovieByTmdbID(Integer id) {
        TMDbFilmResponse movieResponse = client.getMovieDetailsById(apiKey, id);
        return mapper.toMovieResponse(movieResponse);
    }
}
