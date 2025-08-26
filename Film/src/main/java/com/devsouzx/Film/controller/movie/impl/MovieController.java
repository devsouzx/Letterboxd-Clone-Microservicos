package com.devsouzx.Film.controller.movie.impl;

import com.devsouzx.Film.controller.movie.IMovieController;
import com.devsouzx.Film.database.model.MovieLikes;
import com.devsouzx.Film.dto.movie.CrewMemberResponse;
import com.devsouzx.Film.dto.movie.MovieLikesResponse;
import com.devsouzx.Film.dto.movie.MovieResponse;
import com.devsouzx.Film.mapper.FilmMapper;
import com.devsouzx.Film.service.movie.IMovieService;
import com.devsouzx.Film.util.FindUserIdentifierHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/film")
public class MovieController implements IMovieController {
    private final IMovieService movieService;

    public MovieController(IMovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/tmdb/{id}")
    public ResponseEntity<MovieResponse> getMovieByTmdbID(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(movieService.getOrSaveMovieByTmdbID(id));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<MovieResponse> getMovieBySlug(@PathVariable("slug") String slug) {
        return ResponseEntity.ok(movieService.getMovieBySlug(slug));
    }

    @GetMapping("/{slug}/crew")
    public ResponseEntity<List<CrewMemberResponse>> getMovieCrew(@PathVariable("slug") String slug) {
        return ResponseEntity.ok(movieService.getMovieCrewBySlug(slug));
    }

    @GetMapping("/{slug}/likes")
    public ResponseEntity<MovieLikesResponse> getMovieLikes(@PathVariable("slug") String slug) throws Exception {
        return ResponseEntity.ok(movieService.getMovieLikesBySlug(slug));
    }

    @PostMapping("/{slug}/like")
    public ResponseEntity<Void> likeMovieBySlug(@PathVariable("slug") String slug) throws Exception {
        movieService.likeOrUnlikeMovieBySlug(slug);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
