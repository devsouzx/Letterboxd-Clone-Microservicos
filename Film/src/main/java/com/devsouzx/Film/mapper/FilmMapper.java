package com.devsouzx.Film.mapper;

import com.devsouzx.Film.database.model.Movie;
import com.devsouzx.Film.dto.movie.MovieResponse;
import com.devsouzx.Film.dto.tmdb.TMDbFilmResponse;
import org.springframework.stereotype.Service;

@Service
public class FilmMapper {
    public MovieResponse toMovieResponseFromTmdb(TMDbFilmResponse tmdbResponse) {
        return MovieResponse.builder()
                .title(tmdbResponse.getTitle())
                .releaseYear(tmdbResponse.getReleaseDate() != null && tmdbResponse.getReleaseDate().length() >= 4
                        ? tmdbResponse.getReleaseDate().substring(0, 4)
                        : null)
                .synopsis(tmdbResponse.getOverview())
                .tagline(tmdbResponse.getTagline())
                .runtime(tmdbResponse.getRuntime())
                .country(tmdbResponse.getProductionCountries().isEmpty()
                        ? null
                        : tmdbResponse.getProductionCountries().get(0).getName())
                .primaryLanguage(tmdbResponse.getSpokenLanguages().isEmpty()
                        ? null
                        : tmdbResponse.getSpokenLanguages().get(0).getEnglishName())
                .posterUrl(tmdbResponse.getPosterPath() != null
                        ? "https://image.tmdb.org/t/p/w500" + tmdbResponse.getPosterPath()
                        : null)
                .backdropUrl(tmdbResponse.getBackdropPath() != null
                        ? "https://image.tmdb.org/t/p/original" + tmdbResponse.getBackdropPath()
                        : null)
                .tmdbID(tmdbResponse.getId())
                .spokenLanguages(tmdbResponse.getSpokenLanguages().stream()
                        .map(TMDbFilmResponse.Language::getEnglishName)
                        .toList())
                .studios(tmdbResponse.getProductionCompanies().stream()
                        .map(TMDbFilmResponse.Company::getName)
                        .toList())
                .genres(tmdbResponse.getGenres().stream()
                        .map(TMDbFilmResponse.Genre::getName)
                        .toList())
                // .cast()
                // .crew()
                // .releases()
                // .themes()
                // .whereToWatch()
                // .alternativeTitles()
                .build();
    }

    public MovieResponse toMovieResponse(Movie movie) {
        return MovieResponse.builder()
                .identifier(movie.getIdentifier())
                .title(movie.getTitle())
                .releaseYear(movie.getReleaseYear())
                .synopsis(movie.getSynopsis())
                .tagline(movie.getTagline())
                .runtime(movie.getRuntime())
                .country(movie.getCountry())
                .primaryLanguage(movie.getPrimaryLanguage())
                .posterUrl(movie.getPosterUrl())
                .backdropUrl(movie.getBackdropUrl())
                .tmdbID(movie.getTmdbID())
                .spokenLanguages(movie.getSpokenLanguages())
                .studios(movie.getStudios())
                .genres(movie.getGenres())
                .cast(movie.getCast())
                .crew(movie.getCrew())
                .releases(movie.getReleases())
                .themes(movie.getThemes())
                .alternativeTitles(movie.getAlternativeTitles())
                .build();
    }

    public Movie toMovie(MovieResponse movieResponse) {
        return Movie.builder()
                .title(movieResponse.getTitle())
                .releaseYear(movieResponse.getReleaseYear())
                .synopsis(movieResponse.getSynopsis())
                .tagline(movieResponse.getTagline())
                .runtime(movieResponse.getRuntime())
                .country(movieResponse.getCountry())
                .primaryLanguage(movieResponse.getPrimaryLanguage())
                .posterUrl(movieResponse.getPosterUrl())
                .backdropUrl(movieResponse.getBackdropUrl())
                .tmdbID(movieResponse.getTmdbID())
                .spokenLanguages(movieResponse.getSpokenLanguages())
                .studios(movieResponse.getStudios())
                .genres(movieResponse.getGenres())
                .cast(movieResponse.getCast())
                .crew(movieResponse.getCrew())
                .releases(movieResponse.getReleases())
                .themes(movieResponse.getThemes())
                .alternativeTitles(movieResponse.getAlternativeTitles())
                .build();
    }
}
