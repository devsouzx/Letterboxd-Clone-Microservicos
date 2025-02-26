package com.devsouzx.Film.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TMDbFilmCreditsResponse {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("cast")
    private List<Cast> cast;

    @JsonProperty("crew")
    private List<Crew> crew;

    @Data
    public static class Cast {
        @JsonProperty("adult")
        private boolean adult;

        @JsonProperty("gender")
        private int gender;

        @JsonProperty("id")
        private int id;

        @JsonProperty("known_for_department")
        private String knownForDepartment;

        @JsonProperty("name")
        private String name;

        @JsonProperty("original_name")
        private String originalName;

        @JsonProperty("popularity")
        private double popularity;

        @JsonProperty("profile_path")
        private String profilePath;

        @JsonProperty("cast_id")
        private int castId;

        @JsonProperty("character")
        private String character;

        @JsonProperty("credit_id")
        private String creditId;

        @JsonProperty("order")
        private int order;
    }

    @Data
    public static class Crew {
        @JsonProperty("adult")
        private boolean adult;

        @JsonProperty("gender")
        private int gender;

        @JsonProperty("id")
        private int id;

        @JsonProperty("known_for_department")
        private String knownForDepartment;

        @JsonProperty("name")
        private String name;

        @JsonProperty("original_name")
        private String originalName;

        @JsonProperty("popularity")
        private double popularity;

        @JsonProperty("profile_path")
        private String profilePath;

        @JsonProperty("credit_id")
        private String creditId;

        @JsonProperty("department")
        private String department;

        @JsonProperty("job")
        private String job;
    }
}
