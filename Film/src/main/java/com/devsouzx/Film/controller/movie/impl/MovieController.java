package com.devsouzx.Film.controller.movie.impl;

import com.devsouzx.Film.controller.movie.IMovieController;
import com.devsouzx.Film.dto.movie.MovieResponse;
import com.devsouzx.Film.mapper.FilmMapper;
import com.devsouzx.Film.service.movie.IMovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController implements IMovieController {
    private final IMovieService movieService;

    public MovieController(IMovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieByTmdbID(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(movieService.getOrSaveMovieByTmdbID(id));
    }
}
