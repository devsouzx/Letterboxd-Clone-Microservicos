package com.devsouzx.Film.client;

import com.devsouzx.Film.dto.tmdb.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tmdbClient", url = "https://api.themoviedb.org/3")
public interface ITMDbClient {
    @GetMapping(value = "/movie/popular")
    TMDbMovieListResponse getPopularMovies(@RequestParam("api_key") String apiKey, @RequestParam("page") Integer page);

    @GetMapping(value = "/movie/now_playing")
    TMDbMovieListDatesResponse getNowPlayingMovies(@RequestParam("api_key") String apiKey, @RequestParam("page") Integer page);

    @GetMapping(value = "/movie/upcoming")
    TMDbMovieListDatesResponse getUpcomingMovies(@RequestParam("api_key") String apiKey, @RequestParam("page") Integer page);

    @GetMapping(value = "/movie/{movie_id}")
    TMDbFilmResponse getMovieDetailsById(@RequestParam("api_key") String apiKey, @PathVariable("movie_id") Integer movieId);

    @GetMapping(value = "/movie/{movie_id}/alternative_titles")
    TMDbAlternativeTitlesResponse getMovieAlternativeTitles(@RequestParam("api_key") String apiKey, @PathVariable("movie_id") Integer movieId);

    @GetMapping(value = "/movie/{movie_id}/credits")
    TMDbFilmCreditsResponse getMovieCredits(@RequestParam("api_key") String apiKey, @PathVariable("movie_id") Integer movieId);

    @GetMapping(value = "/movie/{movie_id}/images")
    TMDbFilmImagesResponse getMovieImages(@RequestParam("api_key") String apiKey, @PathVariable("movie_id") Integer movieId);

    @GetMapping(value = "/movie/{movie_id}/release_dates")
    TMDbFilmReleaseDatesResponse getMovieReleaseDates(@RequestParam("api_key") String apiKey, @PathVariable("movie_id") Integer movieId);

    @GetMapping(value = "/movie/{movie_id}/similar")
    TMDbMovieListResponse getSimilarMovies(@RequestParam("api_key") String apiKey, @PathVariable("movie_id") Integer movieId);

    @GetMapping(value = "/movie/{movie_id}/translations")
    TMDbFilmTranslationsResponse getMovieTranslations(@RequestParam("api_key") String apiKey, @PathVariable("movie_id") Integer movieId);

    @GetMapping(value = "/movie/{movie_id}/videos")
    TMDbFilmVideosResponse getMovieVideos(@RequestParam("api_key") String apiKey, @PathVariable("movie_id") Integer movieId);

    @GetMapping(value = "/movie/{movie_id}/watch/providers")
    TMDbFilmWatchProvidersResponse getMovieStreamingProviders(@RequestParam("api_key") String apiKey, @PathVariable("movie_id") Integer movieId);
}
