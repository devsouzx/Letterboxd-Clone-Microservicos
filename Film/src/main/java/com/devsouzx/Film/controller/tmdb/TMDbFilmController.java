package com.devsouzx.Film.controller.tmdb;

import com.devsouzx.Film.client.ITMDbClient;
import com.devsouzx.Film.dto.tmdb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/movie")
public class TMDbFilmController {
    @Autowired
    private ITMDbClient tmdbClient;
    @Value("${tmdb.apiKey}")
    private String apiKey;

    @GetMapping("/popular")
    public ResponseEntity<TMDbMovieListResponse> getPopularMovies(@RequestParam("page") Integer page) {
        return ResponseEntity.ok(tmdbClient.getPopularMovies(apiKey, page));
    }

    @GetMapping("/now_playing")
    public ResponseEntity<TMDbMovieListDatesResponse> getNowPlayingMovies(@RequestParam("page") Integer page) {
        return ResponseEntity.ok(tmdbClient.getNowPlayingMovies(apiKey, page));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<TMDbMovieListDatesResponse> getUpcomingMovies(@RequestParam("page") Integer page) {
        return ResponseEntity.ok(tmdbClient.getUpcomingMovies(apiKey, page));
    }

    @GetMapping("/{movie_id}")
    public ResponseEntity<TMDbFilmResponse> getMovieDetailsById(@PathVariable("movie_id") Integer movieId) {
        return ResponseEntity.ok(tmdbClient.getMovieDetailsById(apiKey, movieId));
    }

    @GetMapping("/{movie_id}/alternative_titles")
    public ResponseEntity<TMDbAlternativeTitlesResponse> getMovieAlternativeTitles(@PathVariable("movie_id") Integer movieId) {
        return ResponseEntity.ok(tmdbClient.getMovieAlternativeTitles(apiKey, movieId));
    }

    @GetMapping("/{movie_id}/credits")
    public ResponseEntity<TMDbFilmCreditsResponse> getMovieCredits(@PathVariable("movie_id") Integer movieId) {
        return ResponseEntity.ok(tmdbClient.getMovieCredits(apiKey, movieId));
    }

    @GetMapping("/{movie_id}/images")
    public ResponseEntity<TMDbFilmImagesResponse> getMovieImages(@PathVariable("movie_id") Integer movieId) {
        return ResponseEntity.ok(tmdbClient.getMovieImages(apiKey, movieId));
    }

    @GetMapping("/{movie_id}/release_dates")
    public ResponseEntity<TMDbFilmReleaseDatesResponse> getMovieReleaseDates(@PathVariable("movie_id") Integer movieId) {
        return ResponseEntity.ok(tmdbClient.getMovieReleaseDates(apiKey, movieId));
    }

    @GetMapping("/{movie_id}/similar")
    public ResponseEntity<TMDbMovieListResponse> getSimilarMovies(@PathVariable("movie_id") Integer movieId) {
        return ResponseEntity.ok(tmdbClient.getSimilarMovies(apiKey, movieId));
    }

    @GetMapping("/{movie_id}/translations")
    public ResponseEntity<TMDbFilmTranslationsResponse> getMovieTranslations(@PathVariable("movie_id") Integer movieId) {
        return ResponseEntity.ok(tmdbClient.getMovieTranslations(apiKey, movieId));
    }

    @GetMapping("/{movie_id}/videos")
    public ResponseEntity<TMDbFilmVideosResponse> getMovieVideos(@PathVariable("movie_id") Integer movieId) {
        return ResponseEntity.ok(tmdbClient.getMovieVideos(apiKey, movieId));
    }

    @GetMapping("/{movie_id}/watch/providers")
    public ResponseEntity<TMDbFilmWatchProvidersResponse> getMovieStreamingProviders(@PathVariable("movie_id") Integer movieId) {
        return ResponseEntity.ok(tmdbClient.getMovieStreamingProviders(apiKey, movieId));
    }
}
