package com.devsouzx.Film.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TMDbFilmReleaseDatesResponse {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("results")
    private List<Result> results;

    @Data
    public static class Result {
        @JsonProperty("iso_3166_1")
        private String code;

        @JsonProperty("release_dates")
        private List<ReleaseDate> releaseDates;
    }

    @Data
    public static class ReleaseDate {
        @JsonProperty("certification")
        private String certification;

        @JsonProperty("descriptors")
        private List<String> descriptors;

        @JsonProperty("iso_639_1")
        private String code;

        @JsonProperty("note")
        private String note;

        @JsonProperty("release_date")
        private String releaseDate;

        @JsonProperty("type")
        private Integer type;
    }
}
