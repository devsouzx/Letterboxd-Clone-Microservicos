package com.devsouzx.Film.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
public class FilmDTO {
    private Long id;

    private String title;

    @JsonProperty("original_title")
    private String originalTitle;

    private String overview;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("vote_average")
    private Double rating;

    @JsonProperty("vote_count")
    private Integer voteCount;

    private Integer runtime;

    private List<Genre> genres;

    @JsonProperty("production_countries")
    private List<Country> productionCountries;

    @JsonProperty("spoken_languages")
    private List<Language> spokenLanguages;

    private VideoResults videos;

    @Getter
    @Setter
    public static class Genre {
        private int id;
        private String name;
    }

    @Getter
    @Setter
    public static class Country {
        @JsonProperty("iso_3166_1")
        private String code;
        private String name;
    }

    @Getter
    @Setter
    public static class Language {
        @JsonProperty("iso_639_1")
        private String code;
        private String name;
    }

    @Getter
    @Setter
    public static class VideoResults {
        private List<Video> results;
    }

    @Getter
    @Setter
    public static class Video {
        private String key;
        private String name;
        private String site;
        private String type;
    }
}
