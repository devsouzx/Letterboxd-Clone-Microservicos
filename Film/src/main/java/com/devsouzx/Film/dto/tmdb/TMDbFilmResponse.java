package com.devsouzx.Film.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TMDbFilmResponse {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("original_title")
    private String originalTitle;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("adult")
    private Boolean adult;

    @JsonProperty("budget")
    private Integer budget;

    @JsonProperty("runtime")
    private Integer runtime;

    @JsonProperty("status")
    private String status;

    @JsonProperty("tagline")
    private String tagline;

    @JsonProperty("genres")
    private List<Genre> genres;

    @JsonProperty("production_countries")
    private List<Country> productionCountries;

    @JsonProperty("production_companies")
    private List<Company> productionCompanies;

    @JsonProperty("spoken_languages")
    private List<Language> spokenLanguages;

    @Data
    public static class Genre {
        @JsonProperty("id")
        private int id;
        @JsonProperty("name")
        private String name;
    }

    @Data
    public static class Country {
        @JsonProperty("iso_3166_1")
        private String code;
        @JsonProperty("name")
        private String name;
    }

    @Data
    public static class Language {
        @JsonProperty("iso_639_1")
        private String code;
        @JsonProperty("name")
        private String name;
        @JsonProperty("english_name")
        private String englishName;
    }

    @Data
    public static class Company {
        @JsonProperty("id")
        private int id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("logo_path")
        private String logoPath;
        @JsonProperty("origin_country")
        private String originCountry;
    }
}
