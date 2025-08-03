package com.devsouzx.Film.mapper;

import com.devsouzx.Film.dto.movie.MovieResponse;
import com.devsouzx.Film.dto.tmdb.TMDbFilmResponse;
import org.springframework.stereotype.Service;

@Service
public class FilmMapper {
    public MovieResponse toMovieResponse(TMDbFilmResponse tmdbResponse) {
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
}
